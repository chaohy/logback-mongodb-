/*
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
package com.example.demo;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.status.ErrorStatus;
import com.mongodb.*;

import java.util.Date;
import java.util.Map;


public class MongoDBAppender extends AppenderBase<LoggingEvent> {

    private Mongo _mongo;
    private String _dbHost = "127.0.0.1";
    private int _dbPort = 27017;
    private String _dbName = "admin";

    public String get_dbCollectionName() {
        return _dbCollectionName;
    }

    public void set_dbCollectionName(String _dbCollectionName) {
        this._dbCollectionName = _dbCollectionName;
    }

    private String _dbCollectionName = "logging";
    private DBCollection _collection;

    @Override
    public void start() {
        try {
            _mongo = new Mongo(_dbHost, _dbPort);
            DB db = _mongo.getDB(_dbName);
            _collection = db.getCollection(_dbCollectionName);
        } catch (Exception e) {
            addStatus(new ErrorStatus("Failed to initialize MondoDB", this, e));
            return;
        }
        super.start();
    }

    public void setDbHost(String dbHost) {
        _dbHost = dbHost;
    }

    public void setDbName(String dbName) {
        _dbName = dbName;
    }

    public void setDbPort(int dbPort) {
        _dbPort = dbPort;
    }


    @Override
    public void stop() {
        _mongo.close();
        super.stop();
    }

    @Override
    protected void append(LoggingEvent e) {

        BasicDBObjectBuilder objectBuilder = BasicDBObjectBuilder.start().
                add("ts", new Date(e.getTimeStamp())).
                add("msg", e.getFormattedMessage()).
                add("level", e.getLevel().toString()).
                add("logger", e.getLoggerName()).
                add("thread", e.getThreadName());
        if(e.hasCallerData()) {
            StackTraceElement st = e.getCallerData()[0];
            String callerData = String.format("%s.%s:%d", st.getClassName(), st.getMethodName(), st.getLineNumber());
            objectBuilder.add("caller", callerData);
        }
        Map<String, String> mdc = e.getMdc();
        if(mdc != null && !mdc.isEmpty()) {
            objectBuilder.add("mdc", new BasicDBObject(mdc) );
        }
        _collection.insert(objectBuilder.get());
    }
}
