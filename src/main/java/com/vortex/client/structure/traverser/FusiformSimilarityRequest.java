package com.vortex.client.structure.traverser;

import com.vortex.client.api.traverser.TraversersAPI;
import com.vortex.client.structure.constant.Direction;
import com.vortex.client.structure.constant.Traverser;
import com.vortex.common.util.E;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FusiformSimilarityRequest {

    @JsonProperty("sources")
    private VerticesArgs sources;
    @JsonProperty("label")
    public String label;
    @JsonProperty("direction")
    public String direction;
    @JsonProperty("min_neighbors")
    public int minNeighbors;
    @JsonProperty("alpha")
    public double alpha;
    @JsonProperty("min_similars")
    public int minSimilars;
    @JsonProperty("top")
    public int top;
    @JsonProperty("group_property")
    public String groupProperty;
    @JsonProperty("min_groups")
    public int minGroups;
    @JsonProperty("max_degree")
    public long degree;
    @JsonProperty("capacity")
    public long capacity;
    @JsonProperty("limit")
    public long limit;
    @JsonProperty("with_intermediary")
    public boolean withIntermediary;
    @JsonProperty("with_vertex")
    public boolean withVertex;

    private FusiformSimilarityRequest() {
        this.sources = null;
        this.label = null;
        this.direction = null;
        this.minNeighbors = 0;
        this.degree = Traverser.DEFAULT_MAX_DEGREE;
        this.alpha = 1.0f;
        this.minSimilars = 1;
        this.top = 0;
        this.groupProperty = null;
        this.minGroups = 0;
        this.capacity = Traverser.DEFAULT_CAPACITY;
        this.limit = Traverser.DEFAULT_PATHS_LIMIT;
        this.withIntermediary = false;
        this.withVertex = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return String.format("FusiformSimilarityRequest{sourceVertex=%s," +
                             "label=%s,direction=%s,minNeighbors=%s," +
                             "alpha=%s,minSimilars=%s,top=%s," +
                             "groupProperty=%s,minGroups=%s," +
                             "degree=%s,capacity=%s,limit=%s," +
                             "withIntermediary=%s,withVertex=%s}",
                             this.sources, this.label, this.direction,
                             this.minNeighbors, this.alpha, this.minSimilars,
                             this.top, this.groupProperty, this.minGroups,
                             this.degree, this.capacity, this.limit,
                             this.withIntermediary, this.withVertex);
    }

    public static class Builder {

        private FusiformSimilarityRequest request;
        private VerticesArgs.Builder sourcesBuilder;

        private Builder() {
            this.request = new FusiformSimilarityRequest();
            this.sourcesBuilder = VerticesArgs.builder();
        }

        public VerticesArgs.Builder sources() {
            return this.sourcesBuilder;
        }

        public Builder label(String label) {
            this.request.label = label;
            return this;
        }

        public Builder direction(Direction direction) {
            this.request.direction = direction.toString();
            return this;
        }

        public Builder minNeighbors(int minNeighbors) {
            TraversersAPI.checkPositive(minNeighbors, "min neighbor count");
            this.request.minNeighbors = minNeighbors;
            return this;
        }

        public Builder alpha(double alpha) {
            TraversersAPI.checkAlpha(alpha);
            this.request.alpha = alpha;
            return this;
        }

        public Builder minSimilars(int minSimilars) {
            TraversersAPI.checkPositive(minSimilars, "min similar count");
            this.request.minSimilars = minSimilars;
            return this;
        }

        public Builder top(int top) {
            TraversersAPI.checkPositive(top, "top");
            this.request.top = top;
            return this;
        }

        public Builder groupProperty(String groupProperty) {
            E.checkArgumentNotNull(groupProperty,
                                   "The group property can't be null");
            this.request.groupProperty = groupProperty;
            return this;
        }

        public Builder minGroups(int minGroups) {
            TraversersAPI.checkPositive(minGroups, "min group count");
            this.request.minGroups = minGroups;
            return this;
        }

        public Builder degree(long degree) {
            TraversersAPI.checkDegree(degree);
            this.request.degree = degree;
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

        public Builder withIntermediary(boolean withIntermediary) {
            this.request.withIntermediary = withIntermediary;
            return this;
        }

        public Builder withVertex(boolean withVertex) {
            this.request.withVertex = withVertex;
            return this;
        }

        public FusiformSimilarityRequest build() {
            this.request.sources = this.sourcesBuilder.build();
            E.checkArgument(this.request.sources != null,
                            "Source vertices can't be null");
            TraversersAPI.checkPositive(request.minNeighbors,
                                        "min neighbor count");
            TraversersAPI.checkPositive(request.minSimilars,
                                        "min similar count");
            if (request.groupProperty != null) {
                TraversersAPI.checkPositive(request.minGroups,
                                            "min group count");
            }
            TraversersAPI.checkAlpha(request.alpha);
            TraversersAPI.checkDegree(request.degree);
            TraversersAPI.checkCapacity(this.request.capacity);
            TraversersAPI.checkLimit(this.request.limit);
            return this.request;
        }
    }
}
