package boutique;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Article {

    public IntegerProperty idA = new SimpleIntegerProperty(); //variable names should be exactly as column names in SQL Database Table. In case if you want to use <int> type instead of <IntegerProperty>, then you need to use getter/setter procedures instead of xxxProperty() below
    public StringProperty marque = new SimpleStringProperty();
    public IntegerProperty stock = new SimpleIntegerProperty();
    public IntegerProperty prix = new SimpleIntegerProperty();
    public StringProperty category = new SimpleStringProperty();
    public IntegerProperty tailleOuPointure = new SimpleIntegerProperty();

    public IntegerProperty idAProperty() { //name should be exactly like this [IntegerProperty variable name (idA) + (Property) = idAProperty] (case sensitive)
        return idA;
    }

    public StringProperty marqueProperty() {
        return marque;
    }

    public IntegerProperty stockProperty() {
        return stock;
    }
    

    public IntegerProperty prixProperty() {
        return prix;
    }


    public StringProperty categoryProperty() {
        return category;
    }
    
    public IntegerProperty tailleOuPointureProperty() {
        return tailleOuPointure;
    }
    

    public Article(int idAValue, String marqueValue, int stockValue, int prixValue, String categoryValue, int tailleOuPointureValue) {
        idA.set(idAValue);
        marque.set(marqueValue);
        stock.set(stockValue);
        prix.set(stockValue);
        category.set(categoryValue);
        tailleOuPointure.set(tailleOuPointureValue);

    }

    public Article(){}
}

