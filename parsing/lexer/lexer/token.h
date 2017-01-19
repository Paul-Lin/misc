#ifndef _TOKEN_H
#define _TOKEN_H
#include <string>

namespace tina {
	class Token {
	public:
		int type;
		std::string text;
		Token(int type, std::string text) :type(type), text(text) {}
		std::string toString();
	};
}
#endif // !_TOKEN_H

