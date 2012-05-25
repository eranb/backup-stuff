/**
 * User: EA
 * Date: 21/02/12
 * Time: 22:39
 */
public class Maman15BasicTester {
    public static void main(String[] args) {
        int itemsCounter = 0;

        CD cd1 = new CD("21", 2011, "Adele", 15);
        CD cd2 = new CD("The Best of 1980-1990", 1990, "U2", 14);

        CD cd1Copy = new CD(cd1);

        Video video1 = new Video("Rango", 2011, "Gore Verbinski");
        Video video2 = new Video("The Artist", 2011, "Michel Hazanavicius");

        Video video1Copy = new Video(video1);
        video1Copy.setPublishYear(2008);
        video1Copy.setDirector("Mariano Cohn");
        
        ItemsCollection itemsCollection = new ItemsCollection();
        itemsCollection.addItem(cd1);
        itemsCounter++;
        itemsCollection.addItem(video1);
        itemsCounter++;
        itemsCollection.addItem(cd2);
        itemsCounter++;
        itemsCollection.addItem(video2);
        itemsCounter++;
        itemsCollection.addItem(cd1Copy);
        itemsCounter++;
        itemsCollection.addItem(video1Copy);
        itemsCounter++;

        System.out.println("Number of CDs in the collection: " + itemsCollection.getNumberOfCDs());
        for (int i = 0; i < itemsCounter; i++) {
            System.out.print("Playing item " + (i + 1) + ": ");
            itemsCollection.playItem(i);
        }

        final int YEAR = 2010;
        System.out.println("Videos that were created before " + YEAR + ":");
        itemsCollection.oldiesButGoldies(YEAR);

        System.out.println("ItemsCollection toString:\n" + itemsCollection.toString());
    }
}
