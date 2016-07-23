package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.Vacation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class TripDAO extends AbstractDAO<Long, Trip> {

    private final static Logger LOG = Logger.getLogger(TripDAO.class);
    private static final String SQL_SELECT_ALL_TRIPS = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips";
    private static final String SQL_SELECT_TRIP_BY_ID = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE idtrip=?";
    private static final String SQL_SELECT_LAST_TRIPS = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips ORDER BY idtrip DESC LIMIT 6";

    public List<Trip> findAllTrips() {
        List<Trip> trips = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_TRIPS);
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(st);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return trips;
    }

    public List<Trip> selectLastTrips() {
        List<Trip> trips = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_TRIPS);
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(st);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return trips;
    }

    public void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(Connection cn){
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Trip findEntityById(Long id) {
        Trip trip = new Trip();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TRIP_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
            }
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return trip;
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Trip entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(Trip entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Trip update(Trip entity) {
        throw new UnsupportedOperationException();
    }
}
