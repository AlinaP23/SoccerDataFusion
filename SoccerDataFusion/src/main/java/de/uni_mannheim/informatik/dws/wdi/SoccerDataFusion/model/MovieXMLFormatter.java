/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.SoccerDataFusion.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Club}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class MovieXMLFormatter extends XMLFormatter<Club> {

	ActorXMLFormatter actorFormatter = new ActorXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("movies");
	}

	@Override
	public Element createElementFromRecord(Club record, Document doc) {
		Element movie = doc.createElement("movie");

		movie.appendChild(createTextElement("id", record.getIdentifier(), doc));

		movie.appendChild(createTextElementWithProvenance("title",
				record.getTitle(),
				record.getMergedAttributeProvenance(Club.TITLE), doc));
		movie.appendChild(createTextElementWithProvenance("director",
				record.getDirector(),
				record.getMergedAttributeProvenance(Club.DIRECTOR), doc));
		movie.appendChild(createTextElementWithProvenance("date", record
				.getDate().toString(), record
				.getMergedAttributeProvenance(Club.DATE), doc));

		movie.appendChild(createActorsElement(record, doc));

		return movie;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	protected Element createActorsElement(Club record, Document doc) {
		Element actorRoot = actorFormatter.createRootElement(doc);
		actorRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(Club.ACTORS));

		for (Player a : record.getActors()) {
			actorRoot.appendChild(actorFormatter
					.createElementFromRecord(a, doc));
		}

		return actorRoot;
	}

}
