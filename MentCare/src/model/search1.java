package model;

//Team 5 Leonardo Mazuran


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class search1 {




StringProperty A = new SimpleStringProperty();
StringProperty B = new SimpleStringProperty();
StringProperty C = new SimpleStringProperty();
StringProperty D = new SimpleStringProperty();
StringProperty E = new SimpleStringProperty();
public final StringProperty AProperty() {
    return this.A;
}

public final java.lang.String getA() {
    return this.AProperty().get();
}

public final void setA(final java.lang.String A) {
    this.AProperty().set(A);
}

public final StringProperty BProperty() {
    return this.B;
}

public final java.lang.String getB() {
    return this.BProperty().get();
}

public final void setB(final java.lang.String B) {
    this.BProperty().set(B);
}

public final StringProperty CProperty() {
    return this.C;
}

public final java.lang.String getCn() {
    return this.CProperty().get();
}

public final void setC(final java.lang.String C) {
    this.CProperty().set(C);
}

public final StringProperty DProperty() {
    return this.D;
}

public final java.lang.String getD() {
    return this.DProperty().get();
}

public final void setD(final java.lang.String D) {
    this.DProperty().set(D);
}
public final StringProperty EProperty() {
    return this.E;
}

public final java.lang.String getE() {
    return this.EProperty().get();
}

public final void setE(final java.lang.String E) {
    this.EProperty().set(E);
}



}
