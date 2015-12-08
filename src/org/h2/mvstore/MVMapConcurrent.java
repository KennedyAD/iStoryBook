/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.mvstore;

import org.h2.mvstore.type.MVDataType;
import org.h2.mvstore.type.MVObjectDataType;

/**
 * A class used for backward compatibility.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class MVMapConcurrent<K, V> extends MVMap<K, V> {

    public MVMapConcurrent(MVDataType keyType, MVDataType valueType) {
        super(keyType, valueType);
    }

    /**
     * A builder for this class.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    public static class Builder<K, V> implements
            MapBuilder<MVMapConcurrent<K, V>, K, V> {

        protected MVDataType keyType;
        protected MVDataType valueType;

        /**
         * Create a new builder with the default key and value data types.
         */
        public Builder() {
            // ignore
        }

        /**
         * Set the key data type.
         *
         * @param keyType the key type
         * @return this
         */
        public Builder<K, V> keyType(MVDataType keyType) {
            this.keyType = keyType;
            return this;
        }

        /**
         * Set the key data type.
         *
         * @param valueType the key type
         * @return this
         */
        public Builder<K, V> valueType(MVDataType valueType) {
            this.valueType = valueType;
            return this;
        }

        @Override
        public MVMapConcurrent<K, V> create() {
            if (keyType == null) {
                keyType = new MVObjectDataType();
            }
            if (valueType == null) {
                valueType = new MVObjectDataType();
            }
            return new MVMapConcurrent<K, V>(keyType, valueType);
        }

    }

}
