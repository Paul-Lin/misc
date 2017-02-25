package com.moon.common

import io.netty.channel.group.{ChannelGroup, DefaultChannelGroup}
import io.netty.util.concurrent.GlobalEventExecutor

/**
  * Created by Paul on 2017/2/24.
  */
object Global {
  val group:ChannelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
