package com.summer.nlp.hanlp.demo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * 内存要求
 * 内存120MB以上（-Xms120m -Xmx120m -Xmn64m），标准数据包（35万核心词库+默认用户词典），分词测试正常。全部词典和模型都是惰性加载的，不使用的模型相当于不存在，可以自由删除。
 * HanLP对词典的数据结构进行了长期的优化，可以应对绝大多数场景。哪怕HanLP的词典上百兆也无需担心，因为在内存中被精心压缩过。如果内存非常有限，请使用小词典。HanLP默认使用大词典，同时提供小词典，请参考配置文件章节。
 * 写给正在编译HanLP的开发者
 * 如果你正在编译运行从Github检出的HanLP代码，并且没有下载data缓存，那么首次加载词典/模型会发生一个自动缓存的过程。
 * 自动缓存的目的是为了加速词典载入速度，在下次载入时，缓存的词典文件会带来毫秒级的加载速度。由于词典体积很大，自动缓存会耗费一些时间，请耐心等待。
 * 自动缓存缓存的不是明文词典，而是双数组Trie树、DAWG、AhoCorasickDoubleArrayTrie等数据结构。
 */
@Slf4j
public class HanLPDemo {


	public  void test1(){
		System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
	}

	// 标准分词
	public  void test2(){
		List<Term> termList = StandardTokenizer.segment("商品和服务");
		System.out.println(termList);
	}

	//NLP分词
	public  void test3(){
		System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
		// 注意观察下面两个“希望”的词性、两个“晚霞”的词性
		System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
		System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
	}

	public void  test4(){
//		索引分词
		List<Term> termList = IndexTokenizer.segment("主副食品");
		for (Term term : termList)
		{
			System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
		}
//		说明
//		索引分词IndexTokenizer是面向搜索引擎的分词器，能够对长词全切分，另外通过term.offset可以获取单词在文本中的偏移量。
//		任何分词器都可以通过基类Segment的enableIndexMode方法激活索引模式。
	}

	public  void  test5(){
//		5. N-最短路径分词
		Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
		Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
		String[] testCase = new String[]{
				"今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。",
				"刘喜杰石国祥会见吴亚琴先进事迹报告团成员",
		};
		for (String sentence : testCase)
		{
			System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
		}
//		说明
//		N最短路分词器NShortSegment比最短路分词器慢，但是效果稍微好一些，对命名实体识别能力更强。
//		一般场景下最短路分词的精度已经足够，而且速度比N最短路分词器快几倍，请酌情选择。
//		算法详解
//《N最短路径的Java实现与分词应用》
	}

	public  void test6() throws IOException {
//		6. CRF分词
// 	说明
//		CRF对新词有很好的识别能力，但是开销较大。
//		算法详解
//《CRF中文分词、词性标注与命名实体识别》
		CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
		String[] tests = new String[]{
				"商品和服务",
				"上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观",
				"微软公司於1975年由比爾·蓋茲和保羅·艾倫創立，18年啟動以智慧雲端、前端為導向的大改組。" // 支持繁体中文
		};
		for (String sentence : tests)
		{
			System.out.println(analyzer.analyze(sentence));
		}
	}

	public  void  test7(){
//		7. 极速词典分词
/**
 * 演示极速分词，基于AhoCorasickDoubleArrayTrie实现的词典分词，适用于“高吞吐量”“精度一般”的场合
 * //		说明
 * //		极速分词是词典最长分词，速度极其快，精度一般。
 * //		在i7-6700K上跑出了4500万字每秒的速度。
 * //		算法详解
 * //《Aho Corasick自动机结合DoubleArrayTrie极速多模式匹配》
 */
		String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
		System.out.println(SpeedTokenizer.segment(text));
		long start = System.currentTimeMillis();
		int pressure = 1000000;
		for (int i = 0; i < pressure; ++i)
		{
			SpeedTokenizer.segment(text);
		}
		double costTime = (System.currentTimeMillis() - start) / (double)1000;
		System.out.printf("分词速度：%.2f字每秒", text.length() * pressure / costTime);

	}

	/**
	 * 演示用户词典的动态增删
	 *说明
	 * CustomDictionary是一份全局的用户自定义词典，可以随时增删，影响全部分词器。另外可以在任何分词器中关闭它。通过代码动态增删不会保存到词典文件。
	 * 中文分词≠词典，词典无法解决中文分词，Segment提供高低优先级应对不同场景，请参考FAQ。
	 * 追加词典
	 * CustomDictionary主词典文本路径是data/dictionary/custom/CustomDictionary.txt，用户可以在此增加自己的词语（不推荐）；也可以单独新建一个文本文件，通过配置文件CustomDictionaryPath=data/dictionary/custom/CustomDictionary.txt; 我的词典.txt;来追加词典（推荐）。
	 * 始终建议将相同词性的词语放到同一个词典文件里，便于维护和分享。
	 * 词典格式
	 * 每一行代表一个单词，格式遵从[单词] [词性A] [A的频次] [词性B] [B的频次] ... 如果不填词性则表示采用词典的默认词性。
	 * 词典的默认词性默认是名词n，可以通过配置文件修改：全国地名大全.txt ns;如果词典路径后面空格紧接着词性，则该词典默认是该词性。
	 * 在统计分词中，并不保证自定义词典中的词一定被切分出来。用户可在理解后果的情况下通过Segment#enableCustomDictionaryForcing强制生效。
	 * 关于用户词典的更多信息请参考词典说明一章。
	 * @author hankcs
	 */
	public void test8(){
// 动态增加
		CustomDictionary.add("攻城狮");
		// 强行插入
		CustomDictionary.insert("白富美", "nz 1024");
		// 删除词语（注释掉试试）
//        CustomDictionary.remove("攻城狮");
		System.out.println(CustomDictionary.add("单身狗", "nz 1024 n 1"));
		System.out.println(CustomDictionary.get("单身狗"));

		String text = "攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰";  // 怎么可能噗哈哈！

		// AhoCorasickDoubleArrayTrie自动机扫描文本中出现的自定义词语
		final char[] charArray = text.toCharArray();
		CustomDictionary.parseText(charArray, new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>()
		{
			@Override
			public void hit(int begin, int end, CoreDictionary.Attribute value)
			{
				System.out.printf("[%d:%d]=%s %s\n", begin, end, new String(charArray, begin, end - begin), value);
			}
		});

		// 自定义词典在所有分词器中都有效
		System.out.println(HanLP.segment(text));
	}

	/**
	 * 中国人名识别
	 * 说明
	 * 目前分词器基本上都默认开启了中国人名识别，比如HanLP.segment()接口中使用的分词器等等，用户不必手动开启；上面的代码只是为了强调。
	 * 有一定的误命中率，比如误命中关键年，则可以通过在data/dictionary/person/nr.txt加入一条关键年 A 1来排除关键年作为人名的可能性，也可以将关键年作为新词登记到自定义词典中。
	 * 如果你通过上述办法解决了问题，欢迎向我提交pull request，词典也是宝贵的财富。
	 * 建议NLP用户使用感知机或CRF词法分析器，精度更高。
	 */
	public void test9(){
		String[] testCase = new String[]{
				"签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
				"王国强、高峰、汪洋、张朝阳光着头、韩寒、小四",
				"张浩和胡健康复员回家了",
				"王总和小丽结婚了",
				"编剧邵钧林和稽道青说",
				"这里有关天培的有关事迹",
				"龚学平等领导,邓颖超生前",
		};
		Segment segment = HanLP.newSegment().enableNameRecognize(true);
		for (String sentence : testCase)
		{
			List<Term> termList = segment.seg(sentence);
			System.out.println(termList);
		}
	}

	/**
	 * 音译人名识别
	 * 说明
	 * 目前分词器基本上都默认开启了音译人名识别，用户不必手动开启；上面的代码只是为了强调。
	 */
	public void test10(){
		String[] testCase = new String[]{
				"一桶冰水当头倒下，微软的比尔盖茨、Facebook的扎克伯格跟桑德博格、亚马逊的贝索斯、苹果的库克全都不惜湿身入镜，这些硅谷的科技人，飞蛾扑火似地牺牲演出，其实全为了慈善。",
				"世界上最长的姓名是简森·乔伊·亚历山大·比基·卡利斯勒·达夫·埃利奥特·福克斯·伊维鲁莫·马尔尼·梅尔斯·帕特森·汤普森·华莱士·普雷斯顿。",
		};
		Segment segment = HanLP.newSegment().enableTranslatedNameRecognize(true);
		for (String sentence : testCase)
		{
			List<Term> termList = segment.seg(sentence);
			System.out.println(termList);
		}
	}

	/**
	 * 日本人名识别
	 * 说明
	 * 目前标准分词器默认关闭了日本人名识别，用户需要手动开启；这是因为日本人名的出现频率较低，但是又消耗性能。
	 */
	public void test11(){
		String[] testCase = new String[]{
				"北川景子参演了林诣彬导演的《速度与激情3》",
				"林志玲亮相网友:确定不是波多野结衣？",
		};
		Segment segment = HanLP.newSegment().enableJapaneseNameRecognize(true);
		for (String sentence : testCase)
		{
			List<Term> termList = segment.seg(sentence);
			System.out.println(termList);
		}

	}

	/**
	 * 地名识别
	 * 说明
	 * 目前标准分词器都默认关闭了地名识别，用户需要手动开启；这是因为消耗性能，其实多数地名都收录在核心词典和用户自定义词典中。
	 * 在生产环境中，能靠词典解决的问题就靠词典解决，这是最高效稳定的方法。
	 * 建议对命名实体识别要求较高的用户使用感知机词法分析器。
	 */
	public void test12(){
		String[] testCase = new String[]{
				"武胜县新学乡政府大楼门前锣鼓喧天",
				"蓝翔给宁夏固原市彭阳县红河镇黑牛沟村捐赠了挖掘机",
		};
		Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
		for (String sentence : testCase)
		{
			List<Term> termList = segment.seg(sentence);
			System.out.println(termList);
		}
	}

	/**
	 * 机构名识别
	 *说明
	 * 目前分词器默认关闭了机构名识别，用户需要手动开启；这是因为消耗性能，其实常用机构名都收录在核心词典和用户自定义词典中。
	 * HanLP的目的不是演示动态识别，在生产环境中，能靠词典解决的问题就靠词典解决，这是最高效稳定的方法。
	 * 建议对命名实体识别要求较高的用户使用感知机词法分析器。
	 */
	public void test13(){
		String[] testCase = new String[]{
				"我在上海林原科技有限公司兼职工作，",
				"我经常在台川喜宴餐厅吃饭，",
				"偶尔去地中海影城看电影。",
		};
		Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);
		for (String sentence : testCase)
		{
			List<Term> termList = segment.seg(sentence);
			System.out.println(termList);
		}
	}

	/**
	 * 关键词提取
	 * 说明
	 * 内部采用TextRankKeyword实现，用户可以直接调用TextRankKeyword.getKeywordList(document, size)
	 */
	public void test15(){
		String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
		List<String> keywordList = HanLP.extractKeyword(content, 5);
		System.out.println(keywordList);
	}

	/**
	 * 自动摘要
	 * 说明
	 * 内部采用TextRankSentence实现，用户可以直接调用TextRankSentence.getTopSentenceList(document, size)。
	 */
	public void test16(){
		String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" +
				"算法可以宽泛的分为三类，\n" +
				"一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n" +
				"二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n" +
				"三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
		List<String> sentenceList = HanLP.extractSummary(document, 3);
		System.out.println(sentenceList);
	}

	/**
	 * 短语提取
	 * 说明
	 * 内部采用MutualInformationEntropyPhraseExtractor实现，用户可以直接调用MutualInformationEntropyPhraseExtractor.extractPhrase(text, size)。
	 */
	public void test17(){
		String text = "算法工程师\n" +
				"算法（Algorithm）是一系列解决问题的清晰指令，也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出。如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。不同的算法可能用不同的时间、空间或效率来完成同样的任务。一个算法的优劣可以用空间复杂度与时间复杂度来衡量。算法工程师就是利用算法处理事物的人。\n" +
				"\n" +
				"1职位简介\n" +
				"算法工程师是一个非常高端的职位；\n" +
				"专业要求：计算机、电子、通信、数学等相关专业；\n" +
				"学历要求：本科及其以上的学历，大多数是硕士学历及其以上；\n" +
				"语言要求：英语要求是熟练，基本上能阅读国外专业书刊；\n" +
				"必须掌握计算机相关知识，熟练使用仿真工具MATLAB等，必须会一门编程语言。\n" +
				"\n" +
				"2研究方向\n" +
				"视频算法工程师、图像处理算法工程师、音频算法工程师 通信基带算法工程师\n" +
				"\n" +
				"3目前国内外状况\n" +
				"目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、雷达信号处理、生物医学信号处理等领域的一维信息算法处理。\n" +
				"在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法：机器视觉成为此类算法研究的核心；另外还有2D转3D算法(2D-to-3D conversion)，去隔行算法(de-interlacing)，运动估计运动补偿算法(Motion estimation/Motion Compensation)，去噪算法(Noise Reduction)，缩放算法(scaling)，锐化处理算法(Sharpness)，超分辨率算法(Super Resolution),手势识别(gesture recognition),人脸识别(face recognition)。\n" +
				"在通信物理层等一维信息领域目前常用的算法：无线领域的RRM、RTT，传送领域的调制解调、信道均衡、信号检测、网络优化、信号分解等。\n" +
				"另外数据挖掘、互联网搜索算法也成为当今的热门方向。\n" +
				"算法工程师逐渐往人工智能方向发展。";
		List<String> phraseList = HanLP.extractPhrase(text, 10);
		System.out.println(phraseList);
	}

	/**
	 * 拼音转换   汉字转拼音
	 * 说明
	 * HanLP不仅支持基础的汉字转拼音，还支持声母、韵母、音调、音标和输入法首字母首声母功能。
	 * HanLP能够识别多音字，也能给繁体中文注拼音。
	 * 最重要的是，HanLP采用的模式匹配升级到AhoCorasickDoubleArrayTrie，性能大幅提升，能够提供毫秒级的响应速度！
	 */
	public void test18(){
		String text = "重载不是重任";
		List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);
		System.out.print("原文,");
		for (char c : text.toCharArray())
		{
			System.out.printf("%c,", c);
		}
		System.out.println();

		System.out.print("拼音（数字音调）,");
		for (Pinyin pinyin : pinyinList)
		{
			System.out.printf("%s,", pinyin);
		}
		System.out.println();

		System.out.print("拼音（符号音调）,");
		for (Pinyin pinyin : pinyinList)
		{
			System.out.printf("%s,", pinyin.getPinyinWithToneMark());
		}
		System.out.println();

		System.out.print("拼音（无音调）,");
		for (Pinyin pinyin : pinyinList)
		{
			System.out.printf("%s,", pinyin.getPinyinWithoutTone());
		}
		System.out.println();

		System.out.print("声调,");
		for (Pinyin pinyin : pinyinList)
		{
			System.out.printf("%s,", pinyin.getTone());
		}
		System.out.println();

		System.out.print("声母,");
		for (Pinyin pinyin : pinyinList)
		{
			System.out.printf("%s,", pinyin.getShengmu());
		}
		System.out.println();

		System.out.print("韵母,");
		for (Pinyin pinyin : pinyinList)
		{
			System.out.printf("%s,", pinyin.getYunmu());
		}
		System.out.println();

		System.out.print("输入法头,");
		for (Pinyin pinyin : pinyinList)
		{
			System.out.printf("%s,", pinyin.getHead());
		}
		System.out.println();

	}

	/**
	 * 简繁转换
	 * 说明
	 * HanLP能够识别简繁分歧词，比如打印机=印表機。许多简繁转换工具不能区分“以后”“皇后”中的两个“后”字，HanLP可
	 */
	public void test19(){

		System.out.println(HanLP.convertToTraditionalChinese("用笔记本电脑写程序"));
		System.out.println(HanLP.convertToSimplifiedChinese("「以後等妳當上皇后，就能買士多啤梨慶祝了」"));
	}

	/**
	 * 文本推荐     文本推荐(句子级别，从一系列句子中挑出与输入句子最相似的那一个)
	 * 说明
	 * 在搜索引擎的输入框中，用户输入一个词，搜索引擎会联想出最合适的搜索词，HanLP实现了类似的功能。
	 * 可以动态调节每种识别器的权重
	 */
	public void test20(){
		Suggester suggester = new Suggester();
		String[] titleArray =
				(
						"威廉王子发表演说 呼吁保护野生动物\n" +
								"《时代》年度人物最终入围名单出炉 普京马云入选\n" +
								"“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n" +
								"日本保密法将正式生效 日媒指其损害国民知情权\n" +
								"英报告说空气污染带来“公共健康危机”"
				).split("\\n");
		for (String title : titleArray)
		{
			suggester.addSentence(title);
		}

		System.out.println(suggester.suggest("发言", 1));       // 语义
		System.out.println(suggester.suggest("危机公共", 1));   // 字符
		System.out.println(suggester.suggest("mayun", 1));      // 拼音
	}

	/**
	 * 语义距离  演示词向量的训练与应用
	 */
	public void test21(){
//		WordVectorModel wordVectorModel = trainOrLoadModel();
//		printNearest("中国", wordVectorModel);
//		printNearest("美丽", wordVectorModel);
//		printNearest("购买", wordVectorModel);

//		// 文档向量
//		DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);
//		String[] documents = new String[]{
//				"山东苹果丰收",
//				"农民在江苏种水稻",
//				"奥运会女排夺冠",
//				"世界锦标赛胜出",
//				"中国足球失败",
//		};
//
//		System.out.println(docVectorModel.similarity(documents[0], documents[1]));
//		System.out.println(docVectorModel.similarity(documents[0], documents[4]));
//
//		for (int i = 0; i < documents.length; i++)
//		{
//			docVectorModel.addDocument(i, documents[i]);
//		}

//		printNearestDocument("体育", documents, docVectorModel);
//		printNearestDocument("农业", documents, docVectorModel);
//		printNearestDocument("我要看比赛", documents, docVectorModel);
//		printNearestDocument("要不做饭吧", documents, docVectorModel);
	}

	/**
	 *  依存句法分析（MaxEnt和神经网络句法模型需要-Xms1g -Xmx1g -Xmn512m）
	 *  说明
	 * 内部采用NeuralNetworkDependencyParser实现，用户可以直接调用NeuralNetworkDependencyParser.compute(sentence)
	 * 也可以调用基于ArcEager转移系统的柱搜索依存句法分析器KBeamArcEagerDependencyParser
	 */
	public void test22(){
		CoNLLSentence sentence = HanLP.parseDependency("徐先生还具体帮助他确定了把画雄鹰、松鼠和麻雀作为主攻目标。");
		System.out.println(sentence);
		// 可以方便地遍历它
		for (CoNLLWord word : sentence)
		{
			System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
		}
		// 也可以直接拿到数组，任意顺序或逆序遍历
		CoNLLWord[] wordArray = sentence.getWordArray();
		for (int i = wordArray.length - 1; i >= 0; i--)
		{
			CoNLLWord word = wordArray[i];
			System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
		}
		// 还可以直接遍历子树，从某棵子树的某个节点一路遍历到虚根
		CoNLLWord head = wordArray[12];
		while ((head = head.HEAD) != null)
		{
			if (head == CoNLLWord.ROOT) System.out.println(head.LEMMA);
			else System.out.printf("%s --(%s)--> ", head.LEMMA, head.DEPREL);
		}
	}
	public void test23(){

	}
	public void test24(){

	}








	public static void main(String[] args) throws IOException {
		HanLPDemo hanLPDemo = new HanLPDemo();
//		hanLPDemo.test1();;
//		hanLPDemo.test2();;
//		hanLPDemo.test3();;
//		hanLPDemo.test4();;
//
//		hanLPDemo.test5();;






















			}

}

