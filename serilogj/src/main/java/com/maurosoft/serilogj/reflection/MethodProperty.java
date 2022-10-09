package com.maurosoft.serilogj.reflection;

import java.lang.reflect.Method;

// Copyright 2013-2015 Serilog Contributors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// Values in Serilog are simplified down into a lowest-common-denominator internal
// type system so that there is a better chance of code written with one sink in
// mind working correctly with any other. This technique also makes the programmer
// writing a log event (roughly) in control of the cost of recording that event.
public class MethodProperty implements Property {
    private Method _getter;
    private String _alias;
    
    public MethodProperty(Method getter, String alias) {
        _getter = getter;
        _alias = alias;
    }

    @Override public String getAlias() {
        return _alias;
    }

    @Override public Object getValue(Object obj) {
        try {
            return _getter.invoke(obj);
        } catch( Exception e) {
            // We don't care about any exception, if it fails just return null
            return null;
        }
    }
}