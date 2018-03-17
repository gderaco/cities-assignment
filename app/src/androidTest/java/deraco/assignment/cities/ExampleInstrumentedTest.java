package deraco.assignment.cities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import deraco.assignment.cities.model.City;
import deraco.assignment.cities.util.Utilities;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("deraco.assignment.cities", appContext.getPackageName());

        City[] cities = Utilities.readCities(appContext.getAssets());

        City[] filteredCitiesByPrefix = Utilities.filterCitiesByPrefix(cities, "Montecatini"); //My hometown :)
        assertEquals(filteredCitiesByPrefix[0].getName(), "Montecatini");
        assertEquals(filteredCitiesByPrefix[1].getName(), "Montecatini Terme");
        assertEquals(filteredCitiesByPrefix[2].getName(), "Montecatini-Terme");

        filteredCitiesByPrefix = Utilities.filterCitiesByPrefix(cities, "平仮名");
        assertEquals(filteredCitiesByPrefix.length, 0);
    }
}
