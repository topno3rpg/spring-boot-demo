package mvc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 2016/8/12.
 */
@Repository
public class ContactRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Contact> findAll() {
        return jdbc.query("select id,firstName,lastName,phoneNumber,emailAddress " +
                "from contact order by lastName", new RowMapper<Contact>() {
            @Override
            public Contact mapRow(ResultSet resultSet, int i) throws SQLException {
                Contact contact = new Contact();
                contact.setId(resultSet.getLong(1));
                contact.setFirstName(resultSet.getString(2));
                contact.setLastName(resultSet.getString(3));
                contact.setPhoneNumber(resultSet.getString(4));
                contact.setEmailAddress(resultSet.getString(5));
                return contact;
            }
        });
    }

    public void save(Contact contact) {
        jdbc.update("insert into contact " +
                "(firstName,lastName,phoneNumber,emailAddress)" +
                "values (?,?,?,?)", contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(), contact.getEmailAddress());
    }

}
