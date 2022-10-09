package com.maurosoft.serilogj.sinks.periodicbatching;

//import java.time.Duration;
import org.joda.*;
import org.joda.time.Duration;

public class BatchedConnectionStatus {
	private static final Duration MinimumBackoffPeriod = Duration.standardSeconds(5);
	private static final Duration MaximumBackoffInterval = Duration.standardMinutes(10);

	private static final int FailuresBeforeDroppingBatch = 8;
	private static final int FailuresBeforeDroppingQueue = 10;

	private Duration period;

	int failuresSinceSuccessfulBatch;

	public BatchedConnectionStatus(Duration period) {
		if (period == null || period.getStandardSeconds() == 0) {
			throw new IllegalArgumentException("period");
		}

		this.period = period;
	}

	public void markSuccess() {
		failuresSinceSuccessfulBatch = 0;
	}

	public void markFailure() {
		failuresSinceSuccessfulBatch++;
	}

	public boolean getShouldDropBatch() {
		return failuresSinceSuccessfulBatch >= FailuresBeforeDroppingBatch;
	}

	public boolean getShouldDropQueue() {
		return failuresSinceSuccessfulBatch >= FailuresBeforeDroppingQueue;
	}

	public Duration getNextInterval() {
		// Available, and first failure, just try the batch interval
		if (failuresSinceSuccessfulBatch <= 1) {
			return period;
		}

		// Second failure, start ramping up the interval - first 2x, then 4x,
		// ...
		double backoffFactor = Math.pow(2, failuresSinceSuccessfulBatch - 1);

		// If the period is ridiculously short, give it a boost so we get some
		// visible backoff.
		long backoffPeriod = Math.max(period.getStandardSeconds(), MinimumBackoffPeriod.getStandardSeconds());

		// The "ideal" interval
		long backedOff = (long) (backoffPeriod * backoffFactor);

		// Capped to the maximum interval
		long cappedBackoff = Math.min(backedOff, MaximumBackoffInterval.getStandardSeconds());

		// Unless that's shorter than the period, in which case we'll just apply
		// the period
		long actual = Math.max(period.getStandardSeconds(), cappedBackoff);

		return Duration.standardSeconds(actual);
	}
}
