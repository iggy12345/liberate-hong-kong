package company.Entity.Abstract;

import company.Entity.Interface.ISaveable;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.UUID;

public abstract class ASaveable implements ISaveable {

    private UUID objId;

    public ASaveable() {
        this.objId = UUID.randomUUID();
        this.save();
    }

    @Override
    public UUID save() {
        JSONObject obj = new JSONObject();
        String type = this.getClass().getSimpleName();
        // get the fields
        Field[] fields = ISaveable.getAllFields(this.getClass());
        // add the type and id
        obj.put(ISaveable.TYPE_STR_CONST, ISaveable.removeLeadingLetter(type));
        // add each field
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                obj.put(f.getName(), f.get(this));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ISaveable.saveToFile(obj, ISaveable.removeLeadingLetter(type));
        return this.getObjectId();
    }

    @Override
    public UUID getObjectId() {
        return this.objId;
    }

    @Override
    public void setObjectId(UUID id) {
        this.objId = id;
        this.save();
    }

}
