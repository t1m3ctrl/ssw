package org.sibsutis.comments.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.*;

/**
 * CommentInput
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-26T09:33:16.652656262Z[GMT]")


public class CommentInput   {
    @JsonProperty("userId")

    private Integer userId = null;

    @JsonProperty("resourceId")

    private Integer resourceId = null;

    @JsonProperty("content")

    private String content = null;


    public CommentInput userId(Integer userId) {

        this.userId = userId;
        return this;
    }

    /**
     * Get userId
     * @return userId
     **/

    @Schema(example = "67890", required = true, description = "")

    @NotNull
    public Integer getUserId() {
        return userId;
    }



    public void setUserId(Integer userId) {

        this.userId = userId;
    }

    public CommentInput resourceId(Integer resourceId) {

        this.resourceId = resourceId;
        return this;
    }

    /**
     * Get resourceId
     * @return resourceId
     **/

    @Schema(example = "54321", required = true, description = "")

    @NotNull
    public Integer getResourceId() {
        return resourceId;
    }



    public void setResourceId(Integer resourceId) {

        this.resourceId = resourceId;
    }

    public CommentInput content(String content) {

        this.content = content;
        return this;
    }

    /**
     * Get content
     * @return content
     **/

    @Schema(example = "This is a new comment.", required = true, description = "")

    @NotNull
    public String getContent() {
        return content;
    }



    public void setContent(String content) {

        this.content = content;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentInput commentInput = (CommentInput) o;
        return Objects.equals(this.userId, commentInput.userId) &&
                Objects.equals(this.resourceId, commentInput.resourceId) &&
                Objects.equals(this.content, commentInput.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, resourceId, content);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CommentInput {\n");

        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
        sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
