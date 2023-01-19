/*
 *  Copyright (c) 2002-2023, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *      3. Neither the name of the copyright holder nor the names of its 
 *         contributors may be used to endorse or promote products derived from
 *         this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.eagle.azure.cosmos;

import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.PartitionKey;

/**
 * The Cosmos DB key.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class CosmosKey {
    
    /**
     * Stores the item id.
     */
    private String itemId;
    
    /**
     * Stores the (request) options.
     */
    private CosmosItemRequestOptions options = new CosmosItemRequestOptions();
    
    /**
     * Stores the partition key.
     */
    private PartitionKey partitionKey = PartitionKey.NONE;

    /**
     * Get the item id.
     * 
     * @return the item id.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Get the request options.
     * 
     * @return the request options.
     */
    public CosmosItemRequestOptions getOptions() {
        return options;
    }

    /**
     * Get the partition key.
     * 
     * @return the partition key.
     */
    public PartitionKey getPartitionKey() {
        return partitionKey;
    }
    
    /**
     * Set the item id.
     * 
     * @param itemId the item id.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Set the (request) options.
     * 
     * @param options the (request) options.
     */
    public void setOptions(CosmosItemRequestOptions options) {
        this.options = options;
    }

    /**
     * Set the partition key.
     * 
     * @param partitionKey the partition key.
     */
    public void setPartitionKey(PartitionKey partitionKey) {
        this.partitionKey = partitionKey;
    }
}
