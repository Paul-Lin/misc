# coding:utf8

class VirtualMachine(object):
    def __init__(self):
        self.frames=[]
        self.frame=None
        self.return_value=None
        self.last_exception=None

    def run_code(self,code,global_names=None,local_names=None):
        """ An entry point to execute code using the virtual machine. """
        frame=self.make_frame(code,global_names=global_names,local_names=local_names)
        self.run_frame(frame)

    def make_frame(self,code,callargs={},global_names=None,local_names=None):
        if global_names is not None and local_names is not None:
            local_names=global_names
        elif self.frames:
            global_names=self.frame.global_names
            local_names={}