import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void should_return_0_for_a_gutter_game() {

        for (int i = 0; i < 20; i++) {
            game.roll(0);
        }

        assertEquals(0, game.score());
    }

    @Test
    public void should_return_20_for_1_pin_on_each_roll() {
        for (int i = 0; i < 20; i++) {
            game.roll(1);
        }

        assertEquals(20, game.score());
    }

    @Test
    public void should_return_29_for_a_SPARE_in_the_FIRST_frame_one_pin_down_in_each_other_roll() {

        game.roll(5);
        game.roll(5);

        for (int i = 0; i < 18; i++) {
            game.roll(1);
        }

        assertEquals(29, game.score());
    }

    @Test
    public void should_return_29_for_a_SPARE_in_the_LAST_frame_one_pin_down_in_each_other_roll() {

        for (int i = 0; i < 18; i++) {
            game.roll(1);
        }

        game.roll(5);
        game.roll(5);

        game.roll(1);

        assertEquals(29, game.score());
    }

    @Test
    public void should_return_30_for_a_STRIKE_in_the_FIRST_frame_one_pin_down_in_each_other_roll() {

        game.roll(10);

        for (int i = 0; i < 18; i++) {
            game.roll(1);
        }

        assertEquals(30, game.score());
    }

    @Test
    public void should_return_30_for_a_STRIKE_in_the_LAST_frame_one_pin_down_in_each_other_roll() {

        for (int i = 0; i < 18; i++) {
            game.roll(1);
        }

        game.roll(10);

        game.roll(1);

        game.roll(1);

        assertEquals(30, game.score());
    }

    @Test
    public void should_return_300_for_a_perfect_game() {

        for (int i = 0; i < 12; i++) {
            game.roll(10);
        }

        assertEquals(300, game.score());
    }
}
