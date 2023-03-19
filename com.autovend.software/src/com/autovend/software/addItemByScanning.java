// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188
// Victor Campos
// Bheesha Kumari


package com.autovend.software;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.ElectronicScale;
import com.autovend.devices.SimulationException;
import com.autovend.products.BarcodedProduct;
import java.math.BigDecimal;


/**
 *                         Scenarios to Focus On
 *
 *    1. Laser Scanner: Detects a barcode and signals this to the System.
 *    2. System: Blocks the self-checkout station from further customer interaction.
 *    3. System: Determines the characteristics (weight and cost) of the product associated with the barcode.
 *    4. System:Updates the expected weight from the BaggingArea.
 *    5. System: Signals to the Customer I/O to place the scanned item in the Bagging Area.
 *    6. Bagging Area: Signals to the System that the weight has changed.
 *    7. System:Unblocks the station.
 *
 */


public class addItemByScanning  {

    private Barcode barcode;
    private String description;
    private double weight;
    private BigDecimal cost;
    private BarcodedUnit item;
    private BigDecimal price;
    private Boolean isPerUnit;
    private double expectedWeight;

    /**
     * Constructor
     * @param item - This is the item which the Customer is scanning
     * @param product-The item with the Barcode.
     */
    public addItemByScanning(BarcodedUnit item, BarcodedProduct product){

        // if item trying to add is null
        if (item == null)
            throw new SimulationException(new NullPointerException("item"));

        //  information about the item
        this.item=item;
        this.price=product.getPrice();
        this.weight=item.getWeight();
        this.isPerUnit=product.isPerUnit();
        this.expectedWeight=product.getExpectedWeight();
        this.barcode=product.getBarcode();
        this.description= product.getDescription();

    }

    /**
     *
     * @param item - the item which we are going to scan
     * @param product- the item with the barcode
     * @param controller - system which is handling everything
     * @throws Exception- throws exception if the expected weight change differs the bagging area weight change
     *
     * Also if we try to put Product with null barcode it will throw Simulation Exception as covered inside the BarcodedProduct class
     */
   public void scanItem( BarcodedUnit item, BarcodedProduct product, SystemController controller) throws Exception {

        // creating a barcodeScanner object to scan the items
        BarcodeScanner barcodeScanner = controller.mainScanner;


        // barcodeScanner scans the items and if successful it will notify all the Barcode Scanner observers (Scenario 1 covered)

        if(barcodeScanner.scan(item)){

            // should block out the System from further customer interaction (Scenario 2 covered)

               controller.disable(controller);

            // System determining the characteristics such as weight and cost of the product  (Scenario 3 covered)

               weight= controller.getWeight(item);
               cost=controller.getPrice(product);

             // notifying the customer to place the item in the bagging Area (Scenario 5 covered)

                controller.notifyCustomer_to_put_item_in_BaggingArea();;

             // updates the expected weight from the bagging area (covered Scenario 4)

                double expected_change=controller.expectedWeightUpdate(product);
                double bagging_weight_change= controller.bagging_area_weight_change(controller,product.getBarcode(),weight);


              // If the expected change differs the bagging area weight change throwing a Weight Discrepancy Error (Exception covered)

                if(bagging_weight_change!=expected_change){
                          throw new Exception("Weight Discrepancy Occurred");
           }

              // Otherwise adding the item to the bagging area and if the addition is successful then Electronic Scale Observer will be notified by the Hardware code( covered Scenario 6)
               ElectronicScale baggingArea = controller.baggingArea;
               baggingArea.add(item);

               // unblock the System (covered scenario 7)
                controller.enable(controller);

           }
           }


}
