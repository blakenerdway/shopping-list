
package shoppinglist.beam.products.pojos.targetjson;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@DefaultSchema(JavaBeanSchema.class)
public class MerchandiseClassification implements Serializable {

    @SerializedName("class_id")
    private Long classId;
    @SerializedName("department_id")
    private Long departmentId;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

}
