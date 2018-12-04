package com.google.hashcode.aq;

/**
 * Created by manuel on 13/02/16.
 */
public enum CommandType {
    LOAD {
        @Override
        public String label() {
            return "L";
        }
    }, UNLOAD {
        @Override
        public String label() {
            return "U";
        }
    }, DELIVER {
        @Override
        public String label() {
            return "D";
        }
    }, WAIT {
        @Override
        public String label() {
            return "W";
        }
    };

    public abstract String label();
}
