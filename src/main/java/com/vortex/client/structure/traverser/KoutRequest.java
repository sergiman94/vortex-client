package com.vortex.client.structure.traverser;

import com.vortex.client.api.traverser.TraversersAPI;
import com.vortex.client.structure.constant.Traverser;
import com.vortex.common.util.E;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KoutRequest {

    @JsonProperty("source")
    private Object source;
    @JsonProperty("step")
    public EdgeStep step;
    @JsonProperty("max_depth")
    public int maxDepth;
    @JsonProperty("nearest")
    public boolean nearest = true;
    @JsonProperty("count_only")
    public boolean countOnly = false;
    @JsonProperty("capacity")
    public long capacity = Traverser.DEFAULT_CAPACITY;
    @JsonProperty("limit")
    public long limit = Traverser.DEFAULT_LIMIT;
    @JsonProperty("with_vertex")
    public boolean withVertex = false;
    @JsonProperty("with_path")
    public boolean withPath = false;

    private KoutRequest() {
        this.source = null;
        this.step = null;
        this.maxDepth = Traverser.DEFAULT_MAX_DEPTH;
        this.nearest = true;
        this.countOnly = false;
        this.capacity = Traverser.DEFAULT_CAPACITY;
        this.limit = Traverser.DEFAULT_PATHS_LIMIT;
        this.withVertex = false;
        this.withPath = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return String.format("KoutRequest{source=%s,step=%s,maxDepth=%s" +
                             "nearest=%s,countOnly=%s,capacity=%s," +
                             "limit=%s,withVertex=%s,withPath=%s}",
                             this.source, this.step, this.maxDepth,
                             this.nearest, this.countOnly, this.capacity,
                             this.limit, this.withVertex, this.withPath);
    }

    public static class Builder {

        private KoutRequest request;
        private EdgeStep.Builder stepBuilder;

        private Builder() {
                this.request = new KoutRequest();
                this.stepBuilder = EdgeStep.builder();
        }

        public Builder source(Object source) {
            E.checkNotNull(source, "source");
            this.request.source = source;
            return this;
        }

        public EdgeStep.Builder step() {
            EdgeStep.Builder builder = EdgeStep.builder();
            this.stepBuilder = builder;
            return builder;
        }

        public Builder maxDepth(int maxDepth) {
            TraversersAPI.checkPositive(maxDepth, "max depth");
            this.request.maxDepth = maxDepth;
            return this;
        }

        public Builder nearest(boolean nearest) {
            this.request.nearest = nearest;
            return this;
        }

        public Builder countOnly(boolean countOnly) {
            this.request.countOnly = countOnly;
            return this;
        }

        public Builder capacity(long capacity) {
            TraversersAPI.checkCapacity(capacity);
            this.request.capacity = capacity;
            return this;
        }

        public Builder limit(long limit) {
            TraversersAPI.checkLimit(limit);
            this.request.limit = limit;
            return this;
        }

        public Builder withVertex(boolean withVertex) {
            this.request.withVertex = withVertex;
            return this;
        }

        public Builder withPath(boolean withPath) {
            this.request.withPath = withPath;
            return this;
        }

        public KoutRequest build() {
            E.checkNotNull(this.request.source, "The source can't be null");
            this.request.step = this.stepBuilder.build();
            E.checkNotNull(this.request.step, "step");
            TraversersAPI.checkPositive(this.request.maxDepth, "max depth");
            TraversersAPI.checkCapacity(this.request.capacity);
            TraversersAPI.checkLimit(this.request.limit);
            if (this.request.countOnly) {
                E.checkArgument(!this.request.withVertex &&
                                !this.request.withPath,
                                "Can't return vertex or path " +
                                "when count only is true");
            }
            return this.request;
        }
    }
}
