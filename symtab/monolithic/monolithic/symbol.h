#ifndef _SYMBOL_H
#define _SYMBOL_H
#include <string>

namespace tina {
	class Type {
	public:
		virtual std::string getName() = 0;
		virtual std::string toString()=0;
	};
	class Symbol {
	public:
		std::string *name;
		Type *type;
		Symbol(std::string *nm):name(nm){}
		Symbol(std::string *nm,Type *ty):name(nm),type(ty){}
		std::string getName() { return *name; }
		std::string toString() {
			if (type != nullptr)
				return '<' + getName() + " : " + type->toString();
		}
	};
}
#endif

