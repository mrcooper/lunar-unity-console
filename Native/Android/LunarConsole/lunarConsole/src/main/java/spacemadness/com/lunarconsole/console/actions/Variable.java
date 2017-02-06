//
//  Variable.java
//
//  Lunar Unity Mobile Console
//  https://github.com/SpaceMadness/lunar-unity-console
//
//  Copyright 2017 Alex Lementuev, SpaceMadness.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package spacemadness.com.lunarconsole.console.actions;

import spacemadness.com.lunarconsole.utils.ObjectUtils;

public class Variable extends IdentityEntry
{
    public final VariableType type;
    public final String defaultValue;
    public String value;

    public Variable(int entryId, String name, String value, String defaultValue, VariableType type)
    {
        super(entryId, name);

        this.value = value;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    @Override
    public EntryType getEntryType()
    {
        return EntryType.Variable;
    }

    //region Default value

    public boolean isDefaultValue()
    {
        return ObjectUtils.areEqual(value, defaultValue);
    }

    //endregion

    //region Getters/Setters

    boolean boolValue()
    {
        return value != null && value.length() > 0 && !value.equals("0");
    }

    //endregion
}