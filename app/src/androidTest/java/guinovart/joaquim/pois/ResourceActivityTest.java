package guinovart.joaquim.pois;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by perecullera on 25/8/15.
 */
public class ResourceActivityTest extends ActivityInstrumentationTestCase2<ResourceActivity>  {

    public ResourceActivityTest() {
        super(ResourceActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }
    public void testActivityExists() {
        int position = 52;
        for (int i =0;i< position; i++ ) {
            Intent intent = new Intent(getInstrumentation().getTargetContext(), ResourceActivity.class);
            intent.putExtra("id", position+1);
            ResourceActivity activity = getActivity();
            assertNotNull(activity);
        }
    }
}
