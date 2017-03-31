package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Psychologist {
	private StringProperty PNumber;
	private StringProperty DocID;
	private StringProperty PsychNotes;
	
	public Psychologist(String PNum, String Docid, String PsychologistNotes) {
		this.PNumber = new SimpleStringProperty(PNum);
		this.DocID = new SimpleStringProperty(Docid);
		this.PsychNotes = new SimpleStringProperty(PsychologistNotes);
	}

	public StringProperty getPNumber() {
		return PNumber;
	}
	public void setPNumber(StringProperty pNumber) {
		PNumber = pNumber;
	}
	public StringProperty getDocID() {
		return DocID;
	}
	public void setDocID(StringProperty docID) {
		DocID = docID;
	}
	public StringProperty getPsychNotes() {
		return PsychNotes;
	}
	public void setPsychNotes(StringProperty psychNotes) {
		PsychNotes = psychNotes;
	}
}
