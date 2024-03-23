import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant restaurant1 = Mockito.spy(restaurant);
        LocalTime serviceTime = LocalTime.parse("19:49:00"); //Taking the current time between opening and closing times

        Mockito.when(restaurant1.getCurrentTime()).thenReturn(serviceTime);
        boolean isRestaurantOpen = restaurant1.isRestaurantOpen();

        assertThat(isRestaurantOpen, equalTo(true));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant restaurant1 = Mockito.spy(restaurant);
        LocalTime serviceTime = LocalTime.parse("09:00:00"); //Taking the current time outside opening and closing times

        Mockito.when(restaurant1.getCurrentTime()).thenReturn(serviceTime);
        boolean isRestaurantOpen = restaurant1.isRestaurantOpen();

        assertThat(isRestaurantOpen, equalTo(false));

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //Below tests are written for testing the order values when items are passed/not passed
    // These were written before making changes in any class code. (Failing test cases as a part of TDD)
    @Test
    public void order_value_should_return_corresponding_total_price_of_selected_items_present_in_restaurant_menu(){
        int price = restaurant.getOrderValue("Sweet corn soup","Vegetable lasagne");
        assertThat(price, equalTo(388));
    }

    @Test
    public void order_value_should_return_zero_when_we_do_not_select_any_items(){
        int price = restaurant.getOrderValue(); //Passing no parameters mean we haven't selected any items from menu
        assertThat(price, equalTo(0));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}