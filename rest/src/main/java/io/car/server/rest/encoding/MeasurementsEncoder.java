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
package io.car.server.rest.encoding;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;

import io.car.server.core.entities.Measurement;
import io.car.server.core.entities.Measurements;
import io.car.server.core.util.GeoJSONConstants;
import io.car.server.rest.rights.AccessRights;

/**
 *
 * @author Christian Autermann <autermann@uni-muenster.de>
 * @author Arne de Wall <a.dewall@52north.org>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MeasurementsEncoder extends AbstractEntityEncoder<Measurements> {
    private final EntityEncoder<Measurement> measurementEncoder;
    private final JsonNodeFactory factory;

    @Inject
    public MeasurementsEncoder(JsonNodeFactory factory,
                               EntityEncoder<Measurement> measurementEncoder) {
        super(Measurements.class);
        this.measurementEncoder = measurementEncoder;
        this.factory = factory;
    }

    @Override
    public ObjectNode encode(Measurements t, AccessRights rights,
                             MediaType mediaType) {
        ObjectNode on = factory.objectNode();
        ArrayNode an = on.putArray(GeoJSONConstants.FEATURES_KEY);
        for (Measurement measurement : t) {
            an.add(measurementEncoder.encode(measurement, rights, mediaType));
        }
        on.put(GeoJSONConstants.TYPE_KEY,
               GeoJSONConstants.FEATURE_COLLECTION_TYPE);
        return on;
    }
}
