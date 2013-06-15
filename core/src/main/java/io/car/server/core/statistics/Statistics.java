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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.car.server.core.statistics;

import io.car.server.core.util.Pagination;
import io.car.server.core.util.UpCastingIterable;

/**
 * @author jan
 */
public class Statistics extends UpCastingIterable<Statistic> {
    public static StatisticsBuilder from(
            Iterable<? extends Statistic> delegate) {
        return new StatisticsBuilder(delegate);
    }

    protected Statistics(
            Iterable<? extends Statistic> delegate,
            Pagination pagination, long elements) {
        super(delegate, pagination, elements);
    }

    public static class StatisticsBuilder {
        private Iterable<? extends Statistic> delegate;
        private Pagination pagination;
        private long elements;

        public StatisticsBuilder(
                Iterable<? extends Statistic> delegate) {
            this.delegate = delegate;
        }

        public StatisticsBuilder withPagination(
                Pagination pagination) {
            this.pagination = pagination;
            return this;
        }

        public StatisticsBuilder withElements(
                long elements) {
            this.elements = elements;
            return this;
        }

        public Statistics build() {
            return new Statistics(delegate, pagination, elements);
        }
    }
}
