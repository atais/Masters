package p.lodz.ms.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "chromosomes")
public class Chromosome {

    // for QueryBuilder to be able to find the fields
    public static final String ID_FIELD_NAMW = "id";
    public static final String SCORE_FIELD_NAME = "score";

    @DatabaseField(columnName = ID_FIELD_NAMW, canBeNull = false, id = true)
    private String id;

    @DatabaseField(columnName = SCORE_FIELD_NAME)
    private Double score;

    Chromosome() {
	// all persisted classes must define a no-arg constructor with at least
	// package visibility
    }

    public Chromosome(String name) {
	this.id = name;
    }

    public Chromosome(String id, Double score) {
	this.id = id;
	this.score = score;
    }

    public String getId() {
	return id;
    }

    public Double getScore() {
	return score;
    }

    public void setId(String id) {
	this.id = id;
    }

    public void setScore(Double score) {
	this.score = score;
    }

    @Override
    public int hashCode() {
	return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass()) {
	    return false;
	}
	return id.equals(((Chromosome) other).id);
    }
}