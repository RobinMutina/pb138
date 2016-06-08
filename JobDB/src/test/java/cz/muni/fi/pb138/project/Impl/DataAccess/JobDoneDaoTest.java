package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.DbException;
import cz.muni.fi.pb138.project.Impl.DB.DbCoreApi4EmbeddedBaseX;
import cz.muni.fi.pb138.project.Interfaces.DbCoreApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


/**
 * Created by xtomasch on 6/8/16.
 */
public class JobDoneDaoTest {
    private static final DbCoreApi dbApi = DbCoreApi4EmbeddedBaseX.getInstance();

    @Before
    public void setUp() throws DbException {
        String resourcePath = new File(JobDoneDAO.class.getResource("JobDone.xml").getPath()).getParent();
        dbApi.createCollection("JDDtest", resourcePath);
    }

    @After
    public void tearDown() throws DbException {
        dbApi.dropColection("JDDtest");
    }

    @Test
    public void testUniqueIdZero() throws Exception{
        JobDoneDAO dao = new JobDoneDAO();
        assertThat("There is already a record with 0 id", dao.isUniqueID(0L), is(false));
    }

    @Test //not really a good test, but at lest something
    public void testGetNewUniqueId() throws Exception{
        JobDoneDAO dao = new JobDoneDAO();
        Long id = dao.getNewUniqueID();
        assertThat("Generated id may not be unique", dao.isUniqueID(id), is(true));
    }

    @Test
    public void testRoundtrip() throws Exception{
        JobDoneDAO dao = new JobDoneDAO();
        JobDone jd = new JobDone();
        jd.setUserId(0L);
        jd.setJobTypeId(0L);
        jd.setStrartTime(LocalDateTime.of(2001,1,1,1,1,1));
        jd.setEndTime(LocalDateTime.of(2002,2,2,2,2,2));

        dao.createJobDone(jd);

        assertThat("Jobs should match",dao.getJobDoneById(jd.getId()),equalTo(jd));
    }


}
