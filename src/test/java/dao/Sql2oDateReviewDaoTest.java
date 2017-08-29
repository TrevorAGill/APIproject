package dao;

import models.DateReview;
import models.Question;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDateReviewDaoTest {
    private Sql2oUserDao userDao;
    private Sql2oDateReviewDao dateReviewDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dateReviewDao = new Sql2oDateReviewDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add_InstantiatesNewDateReview() throws Exception {
        DateReview test1 = setupDateReview1();
        assertTrue(test1 instanceof DateReview);
    }

    @Test
    public void getAll_RetrieveAllDateReviews_2() throws Exception {
        DateReview test1  = setupDateReview1();
        dateReviewDao.add(test1);
        DateReview test2  = setupDateReview2();
        dateReviewDao.add(test2);
        assertEquals(2,dateReviewDao.getAll().size());
    }

    @Test
    public void getAllDateReviewsOfSpecificUser() throws Exception {
        User user1  = setupTestUser();
        userDao.add(user1);
        DateReview test1  = setupDateReview1();
        dateReviewDao.add(test1);
        DateReview test2  = setupDateReview2();
        dateReviewDao.add(test2);
        assertEquals(1, dateReviewDao.getAllDateReviewsOfSpecificUser(user1.getId()).size());
    }

    //helper method
    public static User setupTestUser (){
        return new User("Trevor Gill", 30, "male", 24, 32);
    }
    public static DateReview setupDateReview1(){
        return new DateReview(1,"Meh",2,2);
    }
    public static DateReview setupDateReview2 (){
        return new DateReview(1,"Awesome",1,5);
    }

}