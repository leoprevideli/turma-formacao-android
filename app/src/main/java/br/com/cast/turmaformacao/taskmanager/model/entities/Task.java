package br.com.cast.turmaformacao.taskmanager.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task implements Parcelable {


    @JsonIgnore
    private Long _id;

    @JsonProperty("_id")
    private Long webId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonIgnore
    private Label label;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getWebId() {
        return webId;
    }

    public void setWebId(Long webId) {
        this.webId = webId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (get_id() != null ? !get_id().equals(task.get_id()) : task.get_id() != null)
            return false;
        if (getWebId() != null ? !getWebId().equals(task.getWebId()) : task.getWebId() != null)
            return false;
        if (getName() != null ? !getName().equals(task.getName()) : task.getName() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(task.getDescription()) : task.getDescription() != null)
            return false;
        return !(getLabel() != null ? !getLabel().equals(task.getLabel()) : task.getLabel() != null);

    }

    @Override
    public int hashCode() {
        int result = get_id() != null ? get_id().hashCode() : 0;
        result = 31 * result + (getWebId() != null ? getWebId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "_id=" + _id +
                ", webId=" + webId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", label=" + label +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeValue(this.webId);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeParcelable(this.label, 0);
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.webId = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.label = in.readParcelable(Label.class.getClassLoader());
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
