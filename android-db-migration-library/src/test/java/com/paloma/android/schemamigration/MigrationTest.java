package com.paloma.android.schemamigration;

//import com.paloma.dao.DaoMaster;
//import com.paloma.dao.LatestMigration;
import junit.framework.TestCase;

/**
 * Created by kaja on 15/04/2014.
 */
public class MigrationTest extends TestCase {

    public void testPerformMigration() throws MigrationFailedException {
        Migration migrateV3ToV4 = new TestMigrateV3ToV4();
        Migration migrateV2ToV3 = new TestMigrateV2ToV3();

        try {
            migrateV2ToV3.migrate(null, 1);
            fail("Migration should fail, earliest possible version that can be migrated is 2");
        } catch (MigrationFailedException e) {
            //ignore, it's supposed to happen :)
        }
        try {
            migrateV3ToV4.migrate(null, 1);
            fail("Migration should fail, earliest possible version that can be migrated is 2");
        } catch (MigrationFailedException e) {
            //ignore, it's supposed to happen :)
        }

        int migratedTo = 0;
        migratedTo = migrateV3ToV4.migrate(null, 2);
        assertEquals(migratedTo, 4);

        migratedTo = migrateV3ToV4.migrate(null, 3);
        assertEquals(migratedTo, 4);

        migratedTo = migrateV2ToV3.migrate(null, 2);
        assertEquals(migratedTo, 3);

        try {
            migrateV2ToV3.migrate(null, 3);
            fail("Migration should fail, latest possible version that can be migrated is 2");
        } catch (MigrationFailedException e) {
            //ignore, it's supposed to happen :)
        }

        try {
            migrateV3ToV4.migrate(null, 4);
            fail("Migration should fail, latest possible version that can be migrated is 3");
        } catch (MigrationFailedException e) {
            //ignore, it's supposed to happen :)
        }
    }
}
