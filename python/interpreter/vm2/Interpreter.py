# coding:utf-8

what_to_execute={
    "instructions":[("LOAD_VALUE",0),
                    ("STORE_NAME",0),
                    ("LOAD_VALUE",1),
                    ("STORE_NAME",1),
                    ("LOAD_NAME",0),
                    ("LOAD_NAME",1),
                    ("ADD_TWO_VALUES",None),
                    ("PRINT_ANSWER",None)],
    "numbers":[69,4],
    "names":["a","b"]
}

class Interpreter:
    def __init__(self):
        self.stack=[]
        self.environment={}

    def STORE_NAME(self,name):
        val=self.stack.pop()
        self.environment[name]=val

    def LOAD_NAME(self,name):
        val=self.environment[name]
        self.stack.append(val)

    def LOAD_VALUE(self, number):
        self.stack.append(number)

    def ADD_TWO_VALUES(self):
        num1 = self.stack.pop()
        num2 = self.stack.pop()
        self.stack.append(num1 + num2)

    def PRINT_ANSWER(self):
        print(self.stack.pop())

    def parse_argument(self,instruction,argument,what_to_execute):
        """ Understand what the argument to each instruction means."""
        numbers=["LOAD_VALUE"]
        names=["LOAD_NAME","STORE_NAME"]

        if instruction in numbers:
            argument_value=what_to_execute["numbers"][argument]
            return argument_value
        elif instruction in names:
            argument_value =what_to_execute["names"][argument]
            return argument_value

    def run_code(self,what_to_execute):
        instructions=what_to_execute["instructions"]
        for each_step in instructions:
            instruction,argument=each_step
            argument_value= self.parse_argument(instruction,argument,what_to_execute)

            if instruction == "LOAD_VALUE":
                self.LOAD_VALUE(argument_value)
            elif instruction == "LOAD_NAME":
                self.LOAD_NAME(argument_value)
            elif instruction == "PRINT_ANSWER":
                self.PRINT_ANSWER()
            elif instruction=="STORE_NAME":
                self.STORE_NAME(argument_value)
            elif instruction=="ADD_TWO_VALUES":
                self.ADD_TWO_VALUES()


interpreter=Interpreter()
interpreter.run_code(what_to_execute)