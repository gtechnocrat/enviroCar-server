/**
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
package io.car.server.rest.coding;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.format.DateTimeFormatter;

import com.google.inject.Inject;

import io.car.server.core.EntityFactory;
import io.car.server.core.entities.Sensor;
import io.car.server.rest.EntityDecoder;
import io.car.server.rest.EntityEncoder;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class SensorCoder implements EntityEncoder<Sensor>, EntityDecoder<Sensor> {
    private EntityFactory factory;
    private DateTimeFormatter format;

    @Inject
    public SensorCoder(EntityFactory factory, DateTimeFormatter format) {
        this.factory = factory;
        this.format = format;
    }

    @Override
    public Sensor decode(JSONObject j, MediaType mediaType) throws JSONException {
        return factory.createSensor().setName(j.getString(JSONConstants.NAME_KEY));
    }

    @Override
    public JSONObject encode(Sensor t, MediaType mediaType) throws JSONException {
        return new JSONObject()
                .put(JSONConstants.NAME_KEY, t.getName())
                .put(JSONConstants.CREATED_KEY, format.print(t.getCreationDate()))
                .put(JSONConstants.MODIFIED_KEY, format.print(t.getLastModificationDate()));
    }
}