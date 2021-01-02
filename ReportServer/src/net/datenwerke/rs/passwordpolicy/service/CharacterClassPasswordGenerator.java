/*
 *  ReportServer
 *  Copyright (c) 2007 - 2020 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.rs.passwordpolicy.service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;


/**
 * Creates passwords satisfying a given {@link CharacterClassBasedPasswordComplexitySpecification}
 * 
 * 
 *
 */
public class CharacterClassPasswordGenerator implements PasswordGenerator {

	private SecureRandom random = new SecureRandom();
	private int passwordLength;
	private List<CharacterSelectionSpecification> characterSelectionSpecifications;

	private char[] allCharacters = new char[0];
	private int numberOfForcedCharacters;
	private CharacterClassBasedPasswordComplexitySpecification characterClassBasedPasswordComplexitySpecification;
	
	public CharacterClassPasswordGenerator(CharacterClassBasedPasswordComplexitySpecification characterClassBasedPasswordComplexitySpecification) {
		this.characterSelectionSpecifications = characterClassBasedPasswordComplexitySpecification.getCharacterSelectionSpecifications();
		this.passwordLength = characterClassBasedPasswordComplexitySpecification.getPasswordMinLength();
		this.characterClassBasedPasswordComplexitySpecification = characterClassBasedPasswordComplexitySpecification;
		
		/* check if configuration is sane */
		numberOfForcedCharacters = 0;
		for(CharacterSelectionSpecification css : characterSelectionSpecifications){
			numberOfForcedCharacters += css.getMinOccurrences();
		}
		
		if(numberOfForcedCharacters > passwordLength)
			throw new RuntimeException("Invalid configuration supplied to CharacterClassPasswordGenerator. " +
					"The sum of the minimum occurrences across all classes is greater than the requested " +
					"password length");
	
		/* Create the concatenation of all possible characters */
		for(CharacterSelectionSpecification css : characterSelectionSpecifications){
			allCharacters = ArrayUtils.addAll(allCharacters, css.getCharacterClass().getCharacters());
		}
	}



	@Override
	public String newPassword() {
		StringBuffer buf = new StringBuffer();
		
		/* create a random password consisting of characters chosen from all classes */
		for(int i = 0; i < passwordLength; i++){
			buf.append(allCharacters[random.nextInt(allCharacters.length)]);
		}
		
		/* pick the indices which will be used to add characters from specific classes */
		Set<Integer> indices = new HashSet<Integer>();
		while(indices.size() < Math.min(numberOfForcedCharacters,passwordLength - 1)){
			indices.add(random.nextInt(passwordLength - 1));
		}

		/* replace the previously selected indices with characters chosen from a specific class */
		Iterator<Integer> indicesIt = indices.iterator();
		for(CharacterSelectionSpecification css : characterSelectionSpecifications){
			if(css.getMinOccurrences() > 0){
				char c = css.getCharacterClass().getCharacters()[random.nextInt(css.getCharacterClass().getCharacters().length)];
				buf.setCharAt(indicesIt.next(), c);
			}
		}
		
		String password = buf.toString();
		
		if(!this.characterClassBasedPasswordComplexitySpecification.isSatisfiedBy(password)){
			throw new RuntimeException("The password generator failed to create a password satisfying the specification. ");
		}
		
		return password;
	}	

}
