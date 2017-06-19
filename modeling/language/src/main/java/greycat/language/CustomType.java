/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greycat.language;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomType extends ASTNode {
    private final String name;
    private final Map<String, Attribute> attributes;
    private final Map<String, Constant> constants;

    public CustomType(String name) {
        this.name = name;
        this.attributes = new HashMap<>();
        this.constants = new HashMap<>();
    }

    public String name() {
        return name;
    }


    public Collection<Constant> constants() {
        return this.constants.values();
    }

    public void addConstant(Constant constant) {
        this.constants.put(constant.name(), constant);
    }


    public Collection<Attribute> attributes() {
        return attributes.values();
    }

    public Attribute getAttribute(String name) {
        for (Attribute att : attributes()) {
            if (att.name().equals(name)) {
                return att;
            }
        }
        return null;
    }

    public void addAttribute(Attribute att) {
        attributes.put(att.name(), att);
    }
}