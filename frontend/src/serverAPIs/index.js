
const base_url = "http://localhost:8080"

const MyServerAPIs = {
  Test: base_url + "/test",
  PostTest: base_url + "/posttest",
  SignUp: base_url + "/api/user/register",
  SignIn: base_url + "/api/user/login",
  Reset: base_url + "/api/user/reset",
  WhoAmI: base_url + "/api/user/whoami",
  MyInfo: base_url + "/api/user/myinfo",
  UpdateMyInfo: base_url + "/api/user/updatemyinfo",
  SearchUser: base_url + "/api/user/searchuser",
  GetUserInfo: base_url + "/api/user/info",

  IsFriend: base_url + "/api/friend/isfriend",
  DeleteFriend: base_url + "/api/friend/delete",
  MakeFriend: base_url + "/api/friend/makefriend",
  LoadFriend: base_url + "/api/friend/load",
  
  LoadMessages: base_url + "/api/message/load",
  LoadFirstMessages: base_url + "/api/message/loadfirst",
  SendMessage: base_url + "/api/message/send",

  CreateGroup: base_url + "/api/group/create",
  SearchGroup: base_url + "/api/group/search",
  LoadGroup: base_url + "/api/group/load",
  IsInGroup: base_url + "/api/group/isingroup",
  GetGroupInfo: base_url + "/api/group/getinfo",
  QuitGroup: base_url + "/api/group/quit",
  JoinGroup: base_url + "/api/group/join",

  LoadGroupMessages: base_url + "/api/groupmessage/load",
  SendGroupMessage: base_url + "/api/groupmessage/send",
  LoadFirstGroupMessage: base_url + "/api/groupmessage/loadfirst",

  WebSocketEndPoint: base_url + "/just-blah"
}

export default MyServerAPIs
