package com.example.luowenliang.idouban.moviedetail.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailItem {

    /**
     * rating : {"max":10,"average":7.7,"details":{"1":434,"3":17654,"2":2254,"5":14703,"4":27927},"stars":"40","min":0}
     * reviews_count : 3945
     * videos : [{"source":{"literal":"youku","pic":"https://img1.doubanio.com/f/movie/caa07065259b352b164109b6767ea00f83d95221/pics/movie/video-youku.png","name":"优酷视频"},"sample_link":"http://v.youku.com/v_show/id_XMzQyMTYwMDgwOA==.html?tpa=dW5pb25faWQ9MzAwMDA4XzEwMDAwMl8wMl8wMQ&refer=doubanneirongshuchu_bd.xuyang01_douban_201122","video_id":"XMzQyMTYwMDgwOA==","need_pay":true},{"source":{"literal":"qq","pic":"https://img3.doubanio.com/f/movie/0a74f4379607fa731489d7f34daa545df9481fa0/pics/movie/video-qq.png","name":"腾讯视频"},"sample_link":"http://v.qq.com/x/cover/esb9yas9hjdbadw.html?ptag=douban.movie","video_id":"esb9yas9hjdbadw","need_pay":true},{"source":{"literal":"iqiyi","pic":"https://img3.doubanio.com/f/movie/7c9e516e02c6fe445b6559c0dd2a705e8b17d1c9/pics/movie/video-iqiyi.png","name":"爱奇艺视频"},"sample_link":"http://www.iqiyi.com/v_19rrfbd628.html?vfm=m_331_dbdy&fv=4904d94982104144a1548dd9040df241","video_id":"19rrfbd628","need_pay":true}]
     * wish_count : 35859
     * original_title : Secret Superstar
     * blooper_urls : ["http://vt1.doubanio.com/201905291057/e55690338dbe49217073fc3f3f976edf/view/movie/M/302260698.mp4","http://vt1.doubanio.com/201905291057/2bd3583f827df4245c652c9b8292d020/view/movie/M/302260483.mp4","http://vt1.doubanio.com/201905291057/feccb8d18ccabf31865616e82adb456b/view/movie/M/302260239.mp4","http://vt1.doubanio.com/201905291057/edcbd3e161f5c5042a48f4189e013297/view/movie/M/302250862.mp4"]
     * collect_count : 410612
     * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508925590.webp","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508925590.webp","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508925590.webp"}
     * douban_site :
     * year : 2017
     * popular_comments : [{"rating":{"max":5,"value":2,"min":0},"useful_count":5297,"author":{"uid":"68034459","avatar":"https://img3.doubanio.com/icon/u68034459-10.jpg","signature":"（很内向）","alt":"https://www.douban.com/people/68034459/","id":"68034459","name":"小鸭子"},"subject_id":"26942674","content":"女主角身上透露着父亲的气息，她不断向小男生、妈妈、弟弟发脾气，遇到一点不如意就砸东西、砸书包，然后事后再向大家悔过道歉，大家纷纷原谅并且爱她\u2014\u2014这...跟家暴循环有何不同？","created_at":"2018-01-17 01:27:15","id":"1307680024"},{"rating":{"max":5,"value":4,"min":0},"useful_count":1263,"author":{"uid":"nikenike","avatar":"https://img3.doubanio.com/icon/u66874055-270.jpg","signature":"聪明不绝顶，尖锐不湿疣。","alt":"https://www.douban.com/people/nikenike/","id":"66874055","name":"时尚一姐小渡边"},"subject_id":"26942674","content":"傅文佩因不堪家暴与陆振华离婚，陆依萍生活所迫与秦五爷签约大上海歌舞厅沦为天涯歌女。","created_at":"2018-01-20 17:19:54","id":"1269609618"},{"rating":{"max":5,"value":4,"min":0},"useful_count":1267,"author":{"uid":"91298707","avatar":"https://img3.doubanio.com/icon/u91298707-6.jpg","signature":"","alt":"https://www.douban.com/people/91298707/","id":"91298707","name":"Boxer"},"subject_id":"26942674","content":"后妈要我推荐，选了这部，和曾经家暴的父亲坐在一起看完了，迷之尴尬","created_at":"2018-01-20 22:10:03","id":"1309898183"},{"rating":{"max":5,"value":4,"min":0},"useful_count":2370,"author":{"uid":"44642517","avatar":"https://img3.doubanio.com/icon/u44642517-15.jpg","signature":"","alt":"https://www.douban.com/people/44642517/","id":"44642517","name":"龟兹人"},"subject_id":"26942674","content":"典型的阿米尔汗公司出品电影，题材是真相访谈的继续，女儿N部曲之二。母亲这个人物承包了所有泪点；米叔hold住了一个如此放飞却又如此真诚可爱的形象，承包了所有笑点。最后让你又哭又笑地度过仨小时并顺便产生了一些思考。可以说是真·男神了。。","created_at":"2017-10-21 13:38:17","id":"1259868090"}]
     * alt : https://movie.douban.com/subject/26942674/
     * id : 26942674
     * mobile_url : https://movie.douban.com/subject/26942674/mobile
     * photos_count : 233
     * pubdate : 2018-01-19
     * title : 神秘巨星
     * do_count : null
     * has_video : true
     * share_url : https://m.douban.com/movie/subject/26942674
     * seasons_count : null
     * languages : ["印地语","英语"]
     * schedule_url :
     * writers : [{"avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp"},"name_en":"Advait Chandan","name":"阿德瓦·香登","alt":"https://movie.douban.com/celebrity/1379532/","id":"1379532"}]
     * pubdates : ["2017-10-18(印度)","2018-01-19(中国大陆)"]
     * website :
     * tags : ["印度","女性","母爱","励志","成长","温情","音乐","宝莱坞","亲情","剧情"]
     * has_schedule : false
     * durations : ["150分钟"]
     * genres : ["剧情","音乐"]
     * collection : null
     * trailers : [{"medium":"https://img3.doubanio.com/img/trailer/medium/2511084446.jpg?","title":"中国预告片：终极版 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/226083/","small":"https://img3.doubanio.com/img/trailer/small/2511084446.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/951d6186f6a10f1a1405e492d0baf987/view/movie/M/302260083.mp4","id":"226083"},{"medium":"https://img1.doubanio.com/img/trailer/medium/2511074258.jpg?","title":"中国预告片：音乐版 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/226412/","small":"https://img1.doubanio.com/img/trailer/small/2511074258.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/504377f711ec8db6c0ad9459a8f2eee0/view/movie/M/302260412.mp4","id":"226412"},{"medium":"https://img3.doubanio.com/img/trailer/medium/2510226540.jpg?","title":"中国预告片：谁是巨星版 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/225970/","small":"https://img3.doubanio.com/img/trailer/small/2510226540.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/553452dbdc70532f504dd50b889beabd/view/movie/M/302250970.mp4","id":"225970"},{"medium":"https://img3.doubanio.com/img/trailer/medium/2511074360.jpg?","title":"中国预告片：定档版 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/225520/","small":"https://img3.doubanio.com/img/trailer/small/2511074360.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/b7f7232b3c0277431d81143b6ce7ea08/view/movie/M/302250520.mp4","id":"225520"}]
     * episodes_count : null
     * trailer_urls : ["http://vt1.doubanio.com/201905291057/951d6186f6a10f1a1405e492d0baf987/view/movie/M/302260083.mp4","http://vt1.doubanio.com/201905291057/504377f711ec8db6c0ad9459a8f2eee0/view/movie/M/302260412.mp4","http://vt1.doubanio.com/201905291057/553452dbdc70532f504dd50b889beabd/view/movie/M/302250970.mp4","http://vt1.doubanio.com/201905291057/b7f7232b3c0277431d81143b6ce7ea08/view/movie/M/302250520.mp4"]
     * has_ticket : false
     * bloopers : [{"medium":"https://img3.doubanio.com/img/trailer/medium/2511722896.jpg?","title":"花絮：主创特辑 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/226698/","small":"https://img3.doubanio.com/img/trailer/small/2511722896.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/e55690338dbe49217073fc3f3f976edf/view/movie/M/302260698.mp4","id":"226698"},{"medium":"https://img3.doubanio.com/img/trailer/medium/2511358711.jpg?","title":"花絮：彩蛋制作特辑 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/226483/","small":"https://img3.doubanio.com/img/trailer/small/2511358711.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/2bd3583f827df4245c652c9b8292d020/view/movie/M/302260483.mp4","id":"226483"},{"medium":"https://img3.doubanio.com/img/trailer/medium/2510818585.jpg?","title":"花絮：母爱特辑 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/226239/","small":"https://img3.doubanio.com/img/trailer/small/2510818585.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/feccb8d18ccabf31865616e82adb456b/view/movie/M/302260239.mp4","id":"226239"},{"medium":"https://img3.doubanio.com/img/trailer/medium/2510060950.jpg?","title":"花絮：阿米尔·汗角色特辑 (中文字幕)","subject_id":"26942674","alt":"https://movie.douban.com/trailer/225862/","small":"https://img3.doubanio.com/img/trailer/small/2510060950.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/edcbd3e161f5c5042a48f4189e013297/view/movie/M/302250862.mp4","id":"225862"}]
     * clip_urls : ["http://vt1.doubanio.com/201905291057/bb285bb2d8fbf4479c1295ea8ce4835c/view/movie/M/302220605.mp4","http://vt1.doubanio.com/201905291057/52a820e6eb2659ae96639fac680dcf5d/view/movie/M/302220603.mp4","http://vt1.doubanio.com/201905291057/8b965f2e3fa5f339d26ff7680f4531aa/view/movie/M/302220600.mp4","http://vt1.doubanio.com/201905291057/37cd782c4a3b74146f6f0e21fd8c4cc7/view/movie/M/302220599.mp4"]
     * current_season : null
     * casts : [{"avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp"},"name_en":"Zaira Wasim","name":"塞伊拉·沃西","alt":"https://movie.douban.com/celebrity/1373292/","id":"1373292"},{"avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1510229457.27.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1510229457.27.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1510229457.27.webp"},"name_en":"Meher Vij","name":"梅·维贾","alt":"https://movie.douban.com/celebrity/1383897/","id":"1383897"},{"avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13628.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13628.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13628.webp"},"name_en":"Aamir Khan","name":"阿米尔·汗","alt":"https://movie.douban.com/celebrity/1031931/","id":"1031931"},{"avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1510229759.29.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1510229759.29.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1510229759.29.webp"},"name_en":"Raj Arjun","name":"拉杰·阿晶","alt":"https://movie.douban.com/celebrity/1383898/","id":"1383898"}]
     * countries : ["印度"]
     * mainland_pubdate : 2018-01-19
     * photos : [{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2511672998.webp","image":"https://img1.doubanio.com/view/photo/l/public/p2511672998.webp","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2511672998.webp","alt":"https://movie.douban.com/photos/photo/2511672998/","id":"2511672998","icon":"https://img1.doubanio.com/view/photo/s/public/p2511672998.webp"},{"thumb":"https://img3.doubanio.com/view/photo/m/public/p2511720742.webp","image":"https://img3.doubanio.com/view/photo/l/public/p2511720742.webp","cover":"https://img3.doubanio.com/view/photo/sqs/public/p2511720742.webp","alt":"https://movie.douban.com/photos/photo/2511720742/","id":"2511720742","icon":"https://img3.doubanio.com/view/photo/s/public/p2511720742.webp"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2511360607.webp","image":"https://img1.doubanio.com/view/photo/l/public/p2511360607.webp","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2511360607.webp","alt":"https://movie.douban.com/photos/photo/2511360607/","id":"2511360607","icon":"https://img1.doubanio.com/view/photo/s/public/p2511360607.webp"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2510984789.webp","image":"https://img1.doubanio.com/view/photo/l/public/p2510984789.webp","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2510984789.webp","alt":"https://movie.douban.com/photos/photo/2510984789/","id":"2510984789","icon":"https://img1.doubanio.com/view/photo/s/public/p2510984789.webp"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2510984779.webp","image":"https://img1.doubanio.com/view/photo/l/public/p2510984779.webp","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2510984779.webp","alt":"https://movie.douban.com/photos/photo/2510984779/","id":"2510984779","icon":"https://img1.doubanio.com/view/photo/s/public/p2510984779.webp"},{"thumb":"https://img3.doubanio.com/view/photo/m/public/p2510695036.webp","image":"https://img3.doubanio.com/view/photo/l/public/p2510695036.webp","cover":"https://img3.doubanio.com/view/photo/sqs/public/p2510695036.webp","alt":"https://movie.douban.com/photos/photo/2510695036/","id":"2510695036","icon":"https://img3.doubanio.com/view/photo/s/public/p2510695036.webp"},{"thumb":"https://img3.doubanio.com/view/photo/m/public/p2509723470.webp","image":"https://img3.doubanio.com/view/photo/l/public/p2509723470.webp","cover":"https://img3.doubanio.com/view/photo/sqs/public/p2509723470.webp","alt":"https://movie.douban.com/photos/photo/2509723470/","id":"2509723470","icon":"https://img3.doubanio.com/view/photo/s/public/p2509723470.webp"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2509530628.webp","image":"https://img1.doubanio.com/view/photo/l/public/p2509530628.webp","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2509530628.webp","alt":"https://movie.douban.com/photos/photo/2509530628/","id":"2509530628","icon":"https://img1.doubanio.com/view/photo/s/public/p2509530628.webp"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2509013939.webp","image":"https://img1.doubanio.com/view/photo/l/public/p2509013939.webp","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2509013939.webp","alt":"https://movie.douban.com/photos/photo/2509013939/","id":"2509013939","icon":"https://img1.doubanio.com/view/photo/s/public/p2509013939.webp"},{"thumb":"https://img3.doubanio.com/view/photo/m/public/p2552931773.webp","image":"https://img3.doubanio.com/view/photo/l/public/p2552931773.webp","cover":"https://img3.doubanio.com/view/photo/sqs/public/p2552931773.webp","alt":"https://movie.douban.com/photos/photo/2552931773/","id":"2552931773","icon":"https://img3.doubanio.com/view/photo/s/public/p2552931773.webp"}]
     * summary : 少女伊西亚（塞伊拉·沃西 Zaira Wasim 饰）拥有着一副天生的好嗓子，对唱歌充满了热爱的她做梦都想成为一名歌星。然而，伊西亚生活在一个不自由的家庭之中，母亲娜吉玛（梅·维贾 Meher Vij 饰）常常遭到性格爆裂独断专横的父亲法鲁克（拉杰·阿晶 Raj Arjun 饰）的拳脚相向，伊西亚知道，想让父亲支持自己的音乐梦想是完全不可能的事情。
     某日，母亲卖掉了金项链给伊西亚买了一台电脑，很快，伊西亚便发现，虽然无法再现实里实现梦想，但是网络中存在着更广阔的舞台。伊西亚录制了一段蒙着脸自弹自唱的视屏上传到了优兔网上，没想到收获了异常热烈的反响，著名音乐人夏克提（阿米尔·汗 Aamir Khan 饰）亦向她抛出了橄榄枝。©豆瓣
     * clips : [{"medium":"https://img3.doubanio.com/img/trailer/medium/2502120696.jpg?","title":"片段","subject_id":"26942674","alt":"https://movie.douban.com/trailer/222605/","small":"https://img3.doubanio.com/img/trailer/small/2502120696.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/bb285bb2d8fbf4479c1295ea8ce4835c/view/movie/M/302220605.mp4","id":"222605"},{"medium":"https://img3.doubanio.com/img/trailer/medium/2502120924.jpg?","title":"片段","subject_id":"26942674","alt":"https://movie.douban.com/trailer/222603/","small":"https://img3.doubanio.com/img/trailer/small/2502120924.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/52a820e6eb2659ae96639fac680dcf5d/view/movie/M/302220603.mp4","id":"222603"},{"medium":"https://img1.doubanio.com/img/trailer/medium/2502120347.jpg?","title":"片段：米叔歌舞彩蛋","subject_id":"26942674","alt":"https://movie.douban.com/trailer/222600/","small":"https://img1.doubanio.com/img/trailer/small/2502120347.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/8b965f2e3fa5f339d26ff7680f4531aa/view/movie/M/302220600.mp4","id":"222600"},{"medium":"https://img1.doubanio.com/img/trailer/medium/2502120409.jpg?","title":"片段","subject_id":"26942674","alt":"https://movie.douban.com/trailer/222599/","small":"https://img1.doubanio.com/img/trailer/small/2502120409.jpg?","resource_url":"http://vt1.doubanio.com/201905291057/37cd782c4a3b74146f6f0e21fd8c4cc7/view/movie/M/302220599.mp4","id":"222599"}]
     * subtype : movie
     * directors : [{"avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp"},"name_en":"Advait Chandan","name":"阿德瓦·香登","alt":"https://movie.douban.com/celebrity/1379532/","id":"1379532"}]
     * comments_count : 93491
     * popular_reviews : [{"rating":{"max":5,"value":3,"min":0},"title":"硬伤太多 ，只能说\u201c还行\u201d","subject_id":"26942674","author":{"uid":"38743105","avatar":"https://img3.doubanio.com/icon/u38743105-4.jpg","signature":"老老实实，先把报告做好吧","alt":"https://www.douban.com/people/38743105/","id":"38743105","name":"zsn"},"summary":"讲真，这个片子我是冲阿米尔汗去看的，但阿米尔汗在其中只是个配角，虽然他贡献了里面最搞笑的片段，让我很是惊喜，但这剧的硬伤太多了，豆瓣评分略高了。 第一女主成名太过容易了，只是发了一个视频在YouTube上...","alt":"https://movie.douban.com/review/9086692/","id":"9086692"},{"rating":{"max":5,"value":2,"min":0},"title":"政治正确不代表就不是烂片了","subject_id":"26942674","author":{"uid":"phenixus","avatar":"https://img3.doubanio.com/icon/u51349943-2.jpg","signature":"如失如来","alt":"https://www.douban.com/people/phenixus/","id":"51349943","name":"Phenixus"},"summary":"抱着摔跤吧爸爸的水准，或至少同一档次的期望去看《神秘巨星》，结果大失所望，虽然综合了女权，家暴，代沟，早恋，母爱，伊斯兰等诸多深刻或煽情的主题，也有非常政治正确的导向性，比如女性的独立解放，但故事...","alt":"https://movie.douban.com/review/9086961/","id":"9086961"},{"rating":{"max":5,"value":5,"min":0},"title":"《神秘巨星》探讨的家庭、母爱、青春、女权、梦想、自由","subject_id":"26942674","author":{"uid":"lingrui1995","avatar":"https://img1.doubanio.com/icon/u63688511-18.jpg","signature":"一个影迷","alt":"https://www.douban.com/people/lingrui1995/","id":"63688511","name":"凌睿"},"summary":"其实先看《神秘巨星》，后看《摔跤吧！爸爸》是比较好的，如果已经看过了《摔跤吧！爸爸》，完全可以看完《神秘巨星》后再看一遍《摔跤吧！爸爸》。 很多人观看《摔跤吧！爸爸》时，不太理解爸爸私自为女儿选择人...","alt":"https://movie.douban.com/review/9080257/","id":"9080257"},{"rating":{"max":5,"value":5,"min":0},"title":"所以要奋力工作，为了活下去","subject_id":"26942674","author":{"uid":"vitaminJ","avatar":"https://img3.doubanio.com/icon/u58001384-133.jpg","signature":"皇后大道东走九遍","alt":"https://www.douban.com/people/vitaminJ/","id":"58001384","name":"隔壁山田"},"summary":"二刷之后补充一下： 今天带我父母去看了这部片子，我妈从头哭到尾，不是泪点的地方她也在哭，走出影厅后跟我说她哭得脑仁儿疼\u2026\u2026 阿米尔·汗演得是真好。小姑娘用心唱歌那里，他震惊的、含着泪的眼神，给到我一...","alt":"https://movie.douban.com/review/9065689/","id":"9065689"},{"rating":{"max":5,"value":2,"min":0},"title":"不一样的励志女主～","subject_id":"26942674","author":{"uid":"162256508","avatar":"https://img1.doubanio.com/icon/user_normal.jpg","signature":"","alt":"https://www.douban.com/people/162256508/","id":"162256508","name":"滔妹儿会画画"},"summary":"首先，我不质疑电影的思想及内涵，且觉得是很好的，冲着电影的立意和阿米尔汗，我给两星，但是看到女主角的人设，我实在打不出三星。 纵然女主有着以一己之力打破常规的高级思想，但是女主的实际行动及人设和她的...","alt":"https://movie.douban.com/review/9090401/","id":"9090401"},{"rating":{"max":5,"value":4,"min":0},"title":"阿米尔汗的电影为什么总是得高分？","subject_id":"26942674","author":{"uid":"59408815","avatar":"https://img3.doubanio.com/icon/u59408815-23.jpg","signature":"破框而出，做自己","alt":"https://www.douban.com/people/59408815/","id":"59408815","name":"叶籽猫"},"summary":"从《三傻大闹宝莱坞》开始，阿米尔汗这个名字，开始为中国观众所熟知。因为这部电影的好口碑，人们开始愿意为\u201c阿米尔·汗\u201d这个名字买单，随后的《我的个神啊》和《摔跤吧！爸爸》都印证了阿米尔汗不仅是个好演...","alt":"https://movie.douban.com/review/9088361/","id":"9088361"},{"rating":{"max":5,"value":4,"min":0},"title":"没有什么是从天而降的","subject_id":"26942674","author":{"uid":"travelbao","avatar":"https://img3.doubanio.com/icon/u3681338-46.jpg","signature":"读万卷书，行万里路","alt":"https://www.douban.com/people/travelbao/","id":"3681338","name":"姚璐"},"summary":"印度电影的名字总是迷之俗气，之前我给人推荐《摔跤吧！爸爸》就被很多朋友当成了傻子，这部《神秘巨星》，我第一次听到片名时，第一反应也是\u201c这电影能看？\u201d 不过阿米尔汗没有让我失望，这同样是一部关注印度社...","alt":"https://movie.douban.com/review/9086180/","id":"9086180"},{"rating":{"max":5,"value":5,"min":0},"title":"在对女性处境的反思上，《神秘巨星》比《摔跤吧，爸爸》走得更远","subject_id":"26942674","author":{"uid":"lsysiki","avatar":"https://img3.doubanio.com/icon/u52438140-16.jpg","signature":"吐槽分子","alt":"https://www.douban.com/people/lsysiki/","id":"52438140","name":"刘喜根"},"summary":"米叔（阿米尔·汗）是我一度很痴迷，一直很喜欢的男演员。 他凭借2013年的《幻影车神》正式进军中国院线市场，去年的《摔跤吧，爸爸》则是他在中国奠定演员和制作人地位的一部电影。 《摔跤吧，爸爸》让我想起他...","alt":"https://movie.douban.com/review/9111041/","id":"9111041"},{"rating":{"max":5,"value":4,"min":0},"title":"五大关键词解读！男权与罩袍：女性权利的反击","subject_id":"26942674","author":{"uid":"dreamfox","avatar":"https://img3.doubanio.com/icon/u2297669-12.jpg","signature":"公众号：dreamcrowfilm","alt":"https://www.douban.com/people/dreamfox/","id":"2297669","name":"乌鸦火堂"},"summary":"都在吐槽印度电影一言不合就唱歌跳舞，这下好了，想不唱都不行，因为描述的就是唱歌的故事。 阿米尔·汗在《摔跤吧！爸爸》之后再度关注女性题材，只是比起前者\u201c谁说女子不如男\u201d的主题，这部《神秘巨星》则更加...","alt":"https://movie.douban.com/review/9087230/","id":"9087230"},{"rating":{"max":5,"value":1,"min":0},"title":"实在令人失望","subject_id":"26942674","author":{"uid":"115233070","avatar":"https://img1.doubanio.com/icon/u115233070-9.jpg","signature":"","alt":"https://www.douban.com/people/115233070/","id":"115233070","name":"NO.5，1948"},"summary":"特别不懂这部电影怎么能打到这个分数。十分失望，槽点满满。 我实在是太讨厌女主了，她就觉得自己想要什么就应该得到什么，如果不如她愿就生气，我觉得她更她爸是一种人。自己迟到了还硬气的不行，坐在桌子上，以...","alt":"https://movie.douban.com/review/9090176/","id":"9090176"}]
     * ratings_count : 295974
     * aka : ["秘密巨星","隐藏的大明星(台)","打死不离歌星梦(港)","सीक्रेट सुपरस्टार"]
     */

    private RatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String original_title;
    private int collect_count;
    private ImagesBean images;
    private String douban_site;
    private String year;
    private String alt;
    private String id;
    private String mobile_url;
    private int photos_count;
    private String pubdate;
    private String title;
    private Object do_count;
    private boolean has_video;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private String website;
    private boolean has_schedule;
    private Object collection;
    private Object episodes_count;
    private boolean has_ticket;
    private Object current_season;
    private String mainland_pubdate;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private List<VideosBean> videos;
    private List<String> blooper_urls;
    private List<PopularCommentsBean> popular_comments;
    private List<String> languages;
    private List<WritersBean> writers;
    private List<String> pubdates;
    private List<String> tags;
    private List<String> durations;
    private List<String> genres;
    private List<TrailersBean> trailers;
    private List<String> trailer_urls;
    private List<BloopersBean> bloopers;
    private List<String> clip_urls;
    private List<CastsBean> casts;
    private List<String> countries;
    private List<PhotosBean> photos;
    private List<ClipsBean> clips;
    private List<DirectorsBean> directors;
    private List<PopularReviewsBean> popular_reviews;
    private List<String> aka;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public int getPhotos_count() {
        return photos_count;
    }

    public void setPhotos_count(int photos_count) {
        this.photos_count = photos_count;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isHas_schedule() {
        return has_schedule;
    }

    public void setHas_schedule(boolean has_schedule) {
        this.has_schedule = has_schedule;
    }

    public Object getCollection() {
        return collection;
    }

    public void setCollection(Object collection) {
        this.collection = collection;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public boolean isHas_ticket() {
        return has_ticket;
    }

    public void setHas_ticket(boolean has_ticket) {
        this.has_ticket = has_ticket;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getMainland_pubdate() {
        return mainland_pubdate;
    }

    public void setMainland_pubdate(String mainland_pubdate) {
        this.mainland_pubdate = mainland_pubdate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public List<String> getBlooper_urls() {
        return blooper_urls;
    }

    public void setBlooper_urls(List<String> blooper_urls) {
        this.blooper_urls = blooper_urls;
    }

    public List<PopularCommentsBean> getPopular_comments() {
        return popular_comments;
    }

    public void setPopular_comments(List<PopularCommentsBean> popular_comments) {
        this.popular_comments = popular_comments;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<WritersBean> getWriters() {
        return writers;
    }

    public void setWriters(List<WritersBean> writers) {
        this.writers = writers;
    }

    public List<String> getPubdates() {
        return pubdates;
    }

    public void setPubdates(List<String> pubdates) {
        this.pubdates = pubdates;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getDurations() {
        return durations;
    }

    public void setDurations(List<String> durations) {
        this.durations = durations;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<TrailersBean> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailersBean> trailers) {
        this.trailers = trailers;
    }

    public List<String> getTrailer_urls() {
        return trailer_urls;
    }

    public void setTrailer_urls(List<String> trailer_urls) {
        this.trailer_urls = trailer_urls;
    }

    public List<BloopersBean> getBloopers() {
        return bloopers;
    }

    public void setBloopers(List<BloopersBean> bloopers) {
        this.bloopers = bloopers;
    }

    public List<String> getClip_urls() {
        return clip_urls;
    }

    public void setClip_urls(List<String> clip_urls) {
        this.clip_urls = clip_urls;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public List<ClipsBean> getClips() {
        return clips;
    }

    public void setClips(List<ClipsBean> clips) {
        this.clips = clips;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public List<PopularReviewsBean> getPopular_reviews() {
        return popular_reviews;
    }

    public void setPopular_reviews(List<PopularReviewsBean> popular_reviews) {
        this.popular_reviews = popular_reviews;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 7.7
         * details : {"1":434,"3":17654,"2":2254,"5":14703,"4":27927}
         * stars : 40
         * min : 0
         */

        private int max;
        private double average;
        private DetailsBean details;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public static class DetailsBean {
            /**
             * 1 : 434.0
             * 3 : 17654.0
             * 2 : 2254.0
             * 5 : 14703.0
             * 4 : 27927.0
             */

            @SerializedName("1")
            private double _$1;
            @SerializedName("3")
            private double _$3;
            @SerializedName("2")
            private double _$2;
            @SerializedName("5")
            private double _$5;
            @SerializedName("4")
            private double _$4;

            public double get_$1() {
                return _$1;
            }

            public void set_$1(double _$1) {
                this._$1 = _$1;
            }

            public double get_$3() {
                return _$3;
            }

            public void set_$3(double _$3) {
                this._$3 = _$3;
            }

            public double get_$2() {
                return _$2;
            }

            public void set_$2(double _$2) {
                this._$2 = _$2;
            }

            public double get_$5() {
                return _$5;
            }

            public void set_$5(double _$5) {
                this._$5 = _$5;
            }

            public double get_$4() {
                return _$4;
            }

            public void set_$4(double _$4) {
                this._$4 = _$4;
            }
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508925590.webp
         * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508925590.webp
         * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508925590.webp
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class VideosBean {
        /**
         * source : {"literal":"youku","pic":"https://img1.doubanio.com/f/movie/caa07065259b352b164109b6767ea00f83d95221/pics/movie/video-youku.png","name":"优酷视频"}
         * sample_link : http://v.youku.com/v_show/id_XMzQyMTYwMDgwOA==.html?tpa=dW5pb25faWQ9MzAwMDA4XzEwMDAwMl8wMl8wMQ&refer=doubanneirongshuchu_bd.xuyang01_douban_201122
         * video_id : XMzQyMTYwMDgwOA==
         * need_pay : true
         */

        private SourceBean source;
        private String sample_link;
        private String video_id;
        private boolean need_pay;

        public SourceBean getSource() {
            return source;
        }

        public void setSource(SourceBean source) {
            this.source = source;
        }

        public String getSample_link() {
            return sample_link;
        }

        public void setSample_link(String sample_link) {
            this.sample_link = sample_link;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public boolean isNeed_pay() {
            return need_pay;
        }

        public void setNeed_pay(boolean need_pay) {
            this.need_pay = need_pay;
        }

        public static class SourceBean {
            /**
             * literal : youku
             * pic : https://img1.doubanio.com/f/movie/caa07065259b352b164109b6767ea00f83d95221/pics/movie/video-youku.png
             * name : 优酷视频
             */

            private String literal;
            private String pic;
            private String name;

            public String getLiteral() {
                return literal;
            }

            public void setLiteral(String literal) {
                this.literal = literal;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class PopularCommentsBean {
        /**
         * rating : {"max":5,"value":2,"min":0}
         * useful_count : 5297
         * author : {"uid":"68034459","avatar":"https://img3.doubanio.com/icon/u68034459-10.jpg","signature":"（很内向）","alt":"https://www.douban.com/people/68034459/","id":"68034459","name":"小鸭子"}
         * subject_id : 26942674
         * content : 女主角身上透露着父亲的气息，她不断向小男生、妈妈、弟弟发脾气，遇到一点不如意就砸东西、砸书包，然后事后再向大家悔过道歉，大家纷纷原谅并且爱她——这...跟家暴循环有何不同？
         * created_at : 2018-01-17 01:27:15
         * id : 1307680024
         */

        private RatingBeanX rating;
        private int useful_count;
        private AuthorBean author;
        private String subject_id;
        private String content;
        private String created_at;
        private String id;

        public RatingBeanX getRating() {
            return rating;
        }

        public void setRating(RatingBeanX rating) {
            this.rating = rating;
        }

        public int getUseful_count() {
            return useful_count;
        }

        public void setUseful_count(int useful_count) {
            this.useful_count = useful_count;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class RatingBeanX {
            /**
             * max : 5
             * value : 2.0
             * min : 0
             */

            private int max;
            private double value;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class AuthorBean {
            /**
             * uid : 68034459
             * avatar : https://img3.doubanio.com/icon/u68034459-10.jpg
             * signature : （很内向）
             * alt : https://www.douban.com/people/68034459/
             * id : 68034459
             * name : 小鸭子
             */

            private String uid;
            private String avatar;
            private String signature;
            private String alt;
            private String id;
            private String name;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class WritersBean {
        /**
         * avatars : {"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp"}
         * name_en : Advait Chandan
         * name : 阿德瓦·香登
         * alt : https://movie.douban.com/celebrity/1379532/
         * id : 1379532
         */

        private AvatarsBean avatars;
        private String name_en;
        private String name;
        private String alt;
        private String id;

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean {
            /**
             * small : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp
             * large : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp
             * medium : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class TrailersBean {
        /**
         * medium : https://img3.doubanio.com/img/trailer/medium/2511084446.jpg?
         * title : 中国预告片：终极版 (中文字幕)
         * subject_id : 26942674
         * alt : https://movie.douban.com/trailer/226083/
         * small : https://img3.doubanio.com/img/trailer/small/2511084446.jpg?
         * resource_url : http://vt1.doubanio.com/201905291057/951d6186f6a10f1a1405e492d0baf987/view/movie/M/302260083.mp4
         * id : 226083
         */

        private String medium;
        private String title;
        private String subject_id;
        private String alt;
        private String small;
        private String resource_url;
        private String id;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class BloopersBean {
        /**
         * medium : https://img3.doubanio.com/img/trailer/medium/2511722896.jpg?
         * title : 花絮：主创特辑 (中文字幕)
         * subject_id : 26942674
         * alt : https://movie.douban.com/trailer/226698/
         * small : https://img3.doubanio.com/img/trailer/small/2511722896.jpg?
         * resource_url : http://vt1.doubanio.com/201905291057/e55690338dbe49217073fc3f3f976edf/view/movie/M/302260698.mp4
         * id : 226698
         */

        private String medium;
        private String title;
        private String subject_id;
        private String alt;
        private String small;
        private String resource_url;
        private String id;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class CastsBean {
        /**
         * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp"}
         * name_en : Zaira Wasim
         * name : 塞伊拉·沃西
         * alt : https://movie.douban.com/celebrity/1373292/
         * id : 1373292
         */

        private AvatarsBeanX avatars;
        private String name_en;
        private String name;
        private String alt;
        private String id;

        public AvatarsBeanX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanX avatars) {
            this.avatars = avatars;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanX {
            /**
             * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp
             * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp
             * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494080264.12.webp
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class PhotosBean {
        /**
         * thumb : https://img1.doubanio.com/view/photo/m/public/p2511672998.webp
         * image : https://img1.doubanio.com/view/photo/l/public/p2511672998.webp
         * cover : https://img1.doubanio.com/view/photo/sqs/public/p2511672998.webp
         * alt : https://movie.douban.com/photos/photo/2511672998/
         * id : 2511672998
         * icon : https://img1.doubanio.com/view/photo/s/public/p2511672998.webp
         */

        private String thumb;
        private String image;
        private String cover;
        private String alt;
        private String id;
        private String icon;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class ClipsBean {
        /**
         * medium : https://img3.doubanio.com/img/trailer/medium/2502120696.jpg?
         * title : 片段
         * subject_id : 26942674
         * alt : https://movie.douban.com/trailer/222605/
         * small : https://img3.doubanio.com/img/trailer/small/2502120696.jpg?
         * resource_url : http://vt1.doubanio.com/201905291057/bb285bb2d8fbf4479c1295ea8ce4835c/view/movie/M/302220605.mp4
         * id : 222605
         */

        private String medium;
        private String title;
        private String subject_id;
        private String alt;
        private String small;
        private String resource_url;
        private String id;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class DirectorsBean {
        /**
         * avatars : {"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp"}
         * name_en : Advait Chandan
         * name : 阿德瓦·香登
         * alt : https://movie.douban.com/celebrity/1379532/
         * id : 1379532
         */

        private AvatarsBeanXX avatars;
        private String name_en;
        private String name;
        private String alt;
        private String id;

        public AvatarsBeanXX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanXX avatars) {
            this.avatars = avatars;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanXX {
            /**
             * small : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp
             * large : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp
             * medium : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509423054.09.webp
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class PopularReviewsBean {
        /**
         * rating : {"max":5,"value":3,"min":0}
         * title : 硬伤太多 ，只能说“还行”
         * subject_id : 26942674
         * author : {"uid":"38743105","avatar":"https://img3.doubanio.com/icon/u38743105-4.jpg","signature":"老老实实，先把报告做好吧","alt":"https://www.douban.com/people/38743105/","id":"38743105","name":"zsn"}
         * summary : 讲真，这个片子我是冲阿米尔汗去看的，但阿米尔汗在其中只是个配角，虽然他贡献了里面最搞笑的片段，让我很是惊喜，但这剧的硬伤太多了，豆瓣评分略高了。 第一女主成名太过容易了，只是发了一个视频在YouTube上...
         * alt : https://movie.douban.com/review/9086692/
         * id : 9086692
         */

        private RatingBeanXX rating;
        private String title;
        private String subject_id;
        private AuthorBeanX author;
        private String summary;
        private String alt;
        private String id;

        public RatingBeanXX getRating() {
            return rating;
        }

        public void setRating(RatingBeanXX rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public AuthorBeanX getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBeanX author) {
            this.author = author;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class RatingBeanXX {
            /**
             * max : 5
             * value : 3.0
             * min : 0
             */

            private int max;
            private double value;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class AuthorBeanX {
            /**
             * uid : 38743105
             * avatar : https://img3.doubanio.com/icon/u38743105-4.jpg
             * signature : 老老实实，先把报告做好吧
             * alt : https://www.douban.com/people/38743105/
             * id : 38743105
             * name : zsn
             */

            private String uid;
            private String avatar;
            private String signature;
            private String alt;
            private String id;
            private String name;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
