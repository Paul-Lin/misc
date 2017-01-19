#include "Parser.h"
#include <algorithm>

tina::Astree tina::Parser::parse(std::string lexer) {
	static auto astrees = std::vector<Astree>();
	static auto lexer_s = lexer;
	std::for_each(std::begin(elements), std::end(elements), [](tina::Element* e){
		(*e).parse(lexer_s, astrees);
	});
	Astree astree;
	return astree;
}