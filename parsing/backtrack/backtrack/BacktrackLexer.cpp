#include "BacktrackLexer.h"

std::string t::BacktrackLexer::tokenNames[] = {"n/a","EOF","NMAE","[","]",",","="};

const std::string& t::Token::toString() {
	return std::string("type: ").append(BacktrackLexer::tokenNames[type]).append(" value: ").append(value);
}


void t::BacktrackLexer::match(int i) {
	
}

void t::BacktrackLexer::consume() {

}