package org.sibsutis.comments.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;

/**
 * Comment
 */
@Entity
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-26T09:33:16.652656262Z[GMT]")
@Table(schema = "comments", name = "comments")
public class Comment   {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private Integer id = null;

    @JsonProperty("userId")

    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private Integer userId = null;

    @JsonProperty("resourceId")

    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private Integer resourceId = null;

    @JsonProperty("content")

    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String content = null;

    @JsonProperty("createdAt")

    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private OffsetDateTime createdAt = null;


    public Comment id(Integer id) {

        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     **/

    @Schema(example = "12345", description = "")

    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public Comment userId(Integer userId) {

        this.userId = userId;
        return this;
    }

    /**
     * Get userId
     * @return userId
     **/

    @Schema(example = "67890", description = "")

    public Integer getUserId() {
        return userId;
    }



    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Comment resourceId(Integer resourceId) {

        this.resourceId = resourceId;
        return this;
    }

    /**
     * Get resourceId
     * @return resourceId
     **/

    @Schema(example = "54321", description = "")

    public Integer getResourceId() {
        return resourceId;
    }



    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Comment content(String content) {

        this.content = content;
        return this;
    }

    /**
     * Get content
     * @return content
     **/

    @Schema(example = "This is a comment.", description = "")

    public String getContent() {
        return content;
    }



    public void setContent(String content) {
        this.content = content;
    }

    public Comment createdAt(OffsetDateTime createdAt) {

        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get createdAt
     * @return createdAt
     **/

    @Schema(example = "2024-03-22T12:00Z", description = "")

    @Valid
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(this.id, comment.id) &&
                Objects.equals(this.userId, comment.userId) &&
                Objects.equals(this.resourceId, comment.resourceId) &&
                Objects.equals(this.content, comment.content) &&
                Objects.equals(this.createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, resourceId, content, createdAt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Comment {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
        sb.append("    content: ").append(toIndentedString(content)).append("\n");
        sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
