/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.sjts.util.json.regexp;

/**
 * Abstraction for regexp handling.
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
//Changes by JAPAN TOTAL SYSTEM CO.,LTD
//・Changed "package": net.sf.json.regexp → jp.co.sjts.util.json.regexp
public interface RegexpMatcher {
   /**
    * Returns the specified group if the string matches the Pattern.<br>
    * The Pattern will be managed internally by the RegexpMatcher
    * implementation.
    */
   String getGroupIfMatches( String str, int group );

   /**
    * Returns true is the string macthes the Pattern.<br>
    * The Pattern will be managed internally by the RegexpMatcher
    * implementation.
    */
   boolean matches( String str );
}