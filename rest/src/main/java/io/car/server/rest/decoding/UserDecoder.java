/*
 * Copyright (C) 2013  Christian Autermann, Jan Alexander Wirwahn,
 *                     Arne De Wall, Dustin Demuth, Saqib Rasheed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.car.server.rest.decoding;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.vividsolutions.jts.geom.Geometry;

import io.car.server.core.entities.Gender;
import io.car.server.core.entities.User;
import io.car.server.rest.JSONConstants;

/**
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class UserDecoder extends AbstractEntityDecoder<User> {
    private final EntityDecoder<Geometry> geometryDecoder;

    @Inject
    public UserDecoder(EntityDecoder<Geometry> geometryDecoder) {
        super(User.class);
        this.geometryDecoder = geometryDecoder;
    }

    @Override
    public User decode(JsonNode j, MediaType mediaType) {
        User user = getEntityFactory().createUser();
        user.setName(j.path(JSONConstants.NAME_KEY).textValue());
        user.setMail(j.path(JSONConstants.MAIL_KEY).textValue());
        user.setToken(j.path(JSONConstants.TOKEN_KEY).textValue());

        user.setAboutMe(j.path(JSONConstants.ABOUT_ME_KEY).textValue());
        user.setCountry(j.path(JSONConstants.COUNTRY_KEY).textValue());
        user.setDayOfBirth(j.path(JSONConstants.DAY_OF_BIRTH_KEY).textValue());
        user.setFirstName(j.path(JSONConstants.FIRST_NAME_KEY).textValue());
        user.setLastName(j.path(JSONConstants.LAST_NAME_KEY).textValue());
        user.setLanguage(j.path(JSONConstants.LANGUAGE_KEY).textValue());
        JsonNode l = j.path(JSONConstants.LOCATION_KEY);
        if (!l.isMissingNode() && !l.isNull()) {
            user.setLocation(geometryDecoder.decode(l, mediaType));
        }
        String g = j.path(JSONConstants.GENDER_KEY).textValue();
        if (g != null) {
            if (g.equalsIgnoreCase(JSONConstants.MALE)) {
                user.setGender(Gender.MALE);
            } else if (g.equalsIgnoreCase(JSONConstants.FEMALE)) {
                user.setGender(Gender.FEMALE);
            } else {
                throw new WebApplicationException(Status.BAD_REQUEST);
            }
        }
        String u = j.path(JSONConstants.URL_KEY).textValue();
        if (u != null) {
            try {
                user.setUrl(new URL(u));
            } catch (MalformedURLException ex) {
                throw new WebApplicationException(ex, Status.BAD_REQUEST);
            }
        }
        return user;
    }
}
