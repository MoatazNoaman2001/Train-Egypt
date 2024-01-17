package com.example.traineygpt.Model;

public enum train_distinction {
    ASWAN(100 , "Aswan has always been a city of great importance,\n it is considered to be a central market although it’s the smallest of the three touristic cities on the Nile,\n it used to be Ancient Egyptian’s gateway to Africa and was in the ancient time a garrison town for the military campaigns against Nubia.\n Here is all the info about Aswan "),
    LUXOR(200, "Luxor is the most well known recognized City in Upper (Southern) Egypt and the capital of Luxor Governorate,\n known famously for its oldest and most Ancient Egyptian sites. Originally called ‘Thebes’ in ancient Egypt,\n Luxor is often known also as the ‘World's greatest open-air Museum"),
    SOHAG(400, "ss"), ASSIUT(500, "ss"),AlMinia(600, "ss") ,
    BaniSewaf(800, "ss") ,
    CAIRO(1000, "Cairo is the third largest, and second-most populous city in Africa.\n It is the capital city of Egypt and the largest at that- however,\n Memphis was the capital city of Egypt during the Early Dynastic Period which fell between 3,100 and 2,686 BC.\n The city of Cairo boasts varied cultures, social classes under a \nrobust natural environment"),
    Tanta(1100, "ss") , Damanhor(1150, "ss") ,ALEXANDRIA(1200, "ss");

    private int i;
    private String des;

    public int getOrdinal() {
        return i;
    }

    public String getDescription() {
        return des;
    }

    train_distinction(int i, String s) {
        this.i = i;
        this.des = s;
    }
}
