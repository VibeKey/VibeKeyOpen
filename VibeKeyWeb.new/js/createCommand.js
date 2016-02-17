function createCommand(doCommand, cmdString, params){
  var command = {
        "doCmd" : doCommand,
        "cmdString" : cmdString,
        "params" : params
  };
  return command;
}