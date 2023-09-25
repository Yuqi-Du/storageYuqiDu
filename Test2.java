import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test2 {

    public static String getAuthToken() throws IOException {
        String url = "http://localhost:8081/v1/auth";
        String requestBody = "{\"username\": \"cassandra\", \"password\": \"cassandra\"}";

        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-Cassandra-Token", "changeme");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response to get the authToken
                    String jsonResponse = response.toString();
                    int authTokenIndex = jsonResponse.indexOf("\"authToken\":") + 13;
                    int authTokenEndIndex = jsonResponse.indexOf("\"", authTokenIndex);
                    return jsonResponse.substring(authTokenIndex, authTokenEndIndex);
                }
            } else {
                throw new IOException("HTTP request failed with response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 2;

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new APITestTask(i));
            thread.start();
//            Thread.sleep(100);
        }
    }

    static class APITestTask implements Runnable {
        private final int threadNumber;

        public APITestTask(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            String API_URL = "http://localhost:8181/v1/fix/fixCollection"; // Replace with the actual API URL
//            String API_URL = "http://localhost:8181/v1/jsonapi_vector_crud_namespace/jsonapi_vector_crud_collection1"; // Replace with the actual API URL
//            String jsonPayload = "{ \"findOneAndDelete\": { \"sort\": { \"$vector\": [-0.80199015,0.41097593,0.14489663,-0.16607809,-0.83925116,-0.5566555,-0.8209646,0.42352915,-0.16844249,-0.42974877,-0.7397646,0.19792354,0.70252514,0.43424487,-0.4474547,-0.22531295,0.7500932,-0.61345565,0.041077614,0.9247645,-0.7054639,-0.7385212,0.7156352,0.26175058,0.4998057,-0.7709142,0.011794329,0.16496563,0.18454266,-0.86870015,-0.79910254,0.40303075,0.6651783,-0.9120681,-0.016473174,-0.30876625,0.06146276,0.09081209,-0.85666907,-0.35882807,-0.36893666,0.45404124,0.35526776,-0.8233861,0.8118768,0.17754328,-0.25864148,0.83313096,-0.4315033,-0.01145494,0.6861229,-0.32506394,0.974182,0.68600357,0.9524344,0.50095403,0.60427654,-0.2826439,-0.0026874542,-0.86996543,-0.05485642,0.112565756,0.328089,0.81533504,-0.41059422,-0.9092212,-0.95432997,0.109357715,0.22992063,0.099776745,-0.29229116,0.059332848,0.39907587,0.6637938,0.1561029,0.63922596,0.32547116,-0.72652996,-0.13269377,-0.5924878,0.27694893,0.3812114,-0.8913182,-0.9868635,-0.81143165,-0.74918437,0.15898168,-0.74383545,0.37907267,-0.5070996,-0.95327127,0.75115526,0.6634908,-0.7052871,0.6787399,0.053158164,0.377841,-0.022185087,0.40547407,0.507576,0.73077345,0.099692225,0.56477034,0.6013535,0.7539892,-0.9784404,-0.5434382,-0.34433317,-0.012760758,-0.98323965,-0.34474373,-0.85258615,0.23469555,0.5707456,-0.7781199,-0.4775343,0.37885487,0.6593901,-0.07576704,-0.78177154,-0.27156675,-0.7215911,0.23089862,0.8032286,0.8817848,0.28011835,-0.13403678,-0.86993814,0.06230104,0.25827587,0.87890065,-0.4457307,-0.43665457,0.56698394,-0.6872432,-0.6290748,0.8451315,0.9816778,-0.010805488,-0.6554215,-0.7466109,-0.41869736,0.048110127,-0.3554362,-0.14023662,-0.6205698,-0.40939975,-0.35925674,0.9179758,0.44303966,-0.9835031,-0.7351577,0.53043294,0.7520114,-0.10779858,0.30633175,0.75101817,0.0673095,-0.06690633,0.8021984,-0.78367543,-0.5551131,0.9705641,-0.7839503,-0.014085293,0.765296,-0.33882272,-0.5907701,0.34989572,-0.58815706,0.780321,0.41661274,0.47801363,0.8451499,-0.14811707,-0.90963316,-0.23133004,0.7677108,0.7572262,0.010422945,0.22007608,0.48319197,0.030467868,0.33097482,0.9817693,-0.77712524,0.50348675,-0.0052257776,-0.87326086,-0.916991,-0.43482482,0.96412337,-0.43246174,-0.10452449,-0.17161763,0.6584567,0.6603054,0.09980011,0.76924825,-0.07135558,-0.5838157,0.41323435,-0.23711419,-0.18073392,-0.8309889,0.87959504,-0.23640311,-0.34000182,-0.8885548,0.44534123,0.07520044,0.18006968,0.17025983,0.44773066,-0.33292747,-0.7441478,-0.6121366,-0.15413642,-0.71516454,0.3877151,-0.7648709,-0.6217607,0.18325973,-0.7288288,-0.6946658,-0.55635154,0.20604002,0.9982157,0.61679184,0.8855673,0.71714616,0.0230062,-0.44983518,-0.8202921,-0.35287583,-0.51671994,-0.6856823,-0.8976921,0.7819867,0.6230612,0.10196614,-0.22840893,-0.9496063,0.6573992,-0.12534869,-0.8586023,-0.35387158,-0.39349484,0.3982718,0.3752638,-0.4218681,0.46134317,-0.09060919,-0.011507869,0.92151904,-0.0116068125,-0.9161389,-0.18146944,0.43326402,-0.09294152,0.039316654,-0.46999824,-0.8899276,-0.145558,-0.5506607,0.8960507,-0.95095897,0.17060149,0.77845335,-0.9054651,0.0010175705,0.18080533,-0.25639594,-0.05548477,0.19028664,-0.6861198,0.7307688,0.7702589,-0.7481849,0.47787917,0.6874119,-0.5424324,-0.020550013,-0.07738018,-0.60354936,0.4579593,-0.5128304,0.06605816,-0.2796725,-0.14643478,0.3939129,-0.9328934,-0.12897992,-0.8706622,-0.18486953,0.31675863,0.78092504,-0.3247025,-0.12329686,-0.5760925,-0.23062062,0.08236086,0.786216,-0.9700906,-0.24981642,-0.520713,0.36096525,-0.8591013,0.8182101,-0.8797685,0.5109577,0.26950645,-0.9194001,-0.5366777,0.2292496,0.08458388,0.73397255,-0.47217453,-0.09679139,-0.55828464,0.5886172,0.48437917,0.5118767,-0.5221826,-0.75991774,0.47919858,0.83101654,0.1753974,0.90554905,0.5351106,-0.025658011,0.61769116,-0.62433076,0.082034945,0.3338126,0.6528243,-0.33774543,-0.024407148,-0.34807265,0.35748518,-0.39034855,0.649675,-0.99587643,0.43874073,0.12540007,0.8580406,0.8603884,0.3432201,-0.30231512,-0.01137495,0.13396394,0.56881976,-0.29178238,0.5357082,0.7447908,-0.3855083,-0.29412186,0.49921775,0.29052103,-0.31605065,-0.45374596,-0.58677864,0.02427113,0.110910535,-0.82600915,-0.2906438,-0.38730228,-0.6653191,0.15867746,-0.8259101,0.7487023,-0.13039589,0.0021585226,-0.50395656,-0.6904695,0.23055542,0.10828817,-0.8878263,-0.39605677,-0.744112,0.31901932,0.69666004,0.5934557,0.17191994,0.5284606,0.29738557,0.58556175,-0.56165445,-0.67455506,-0.9282763,-0.4041555,-0.32276535,0.4028175,0.2219348,-0.29338467,-0.49879956,0.9069027,-0.3468753,0.68918955,0.9067589,-0.43648112,0.30192602,-0.18671644,-0.9189942,0.66880953,-0.6033989,0.0797683,-0.6160977,0.5166261,-0.8498528,-0.44715357,-0.77868414,0.97306275,0.7544812,0.5365702,0.22840941,0.4080758,-0.11060572,-0.92829406,-0.7418573,-0.4597386,-0.70551383,0.6867242,-0.8590572,0.7113826,-0.058303356,0.11685097,-0.019353986,-0.76903903,0.8126528,0.4346149,-0.78348184,0.5482626,0.70732236,-0.12762725,-0.94435847,-0.2693678,-0.47679973,-0.2486682,-0.18536508,-0.054050326,0.33845913,0.083332896,-0.23884475,0.036821365,0.8554313,-0.6669524,-0.8231615,-0.20994401,-0.5840199,-0.19750035,0.44339085,-0.11665928,0.14752495,0.44137466,0.25783718,-0.25059724,0.7537222,-0.5588385,-0.20688033,0.5351409,0.09934044,-0.3842981,-0.6758065,0.029330134,0.78321815,-0.2865628,0.031640053,0.7488512,-0.7688787,-0.113031745,-0.72500706,-0.54743147,-0.9871597,-0.46579945,0.40309846,0.11779714,0.79225147,-0.3757193,0.35179043,-0.9281646,-0.09117222,-0.03605652,0.9890996,-0.4933653,0.5739819,-0.78976023,-0.06947935,-0.71410286,0.21168876,0.8896028,-0.20682096,0.8295686,0.9023119,0.36778557,0.42595124,-0.7390839,0.17997873,-0.71142066,0.2868471,0.037449956,0.72197604,-0.84916544,-0.010350585,0.35147893,-0.45552218,0.6438831,0.16555929,-0.14712286,0.12974942,-0.6370734,0.64531946,0.17078066,0.059300303,0.36153746,0.9321017,0.52875173,-0.024945378,0.482558,0.780764,0.97702014,0.45248973,-0.11291742,0.106529355,-0.29543233,-0.7818842,-0.94917357,-0.49639153,-0.8768648,0.30716503,0.6262189,-0.6440718,0.29604387,0.62067246,0.9164697,-0.7316791,-0.54041123,0.9453008,-0.25466466,0.6439154,0.27753544,-0.9584696,-0.26466882,0.39495087,-0.21500587,-0.2602601,-0.53913045,0.61893594,0.20298588,0.11899376,0.6857393,0.6253978,-0.2930572,-0.5539483,-0.4778478,0.8920473,-0.63998413,0.44448745,-0.51175976,0.7009145,-0.3945192,0.124366164,-0.7494123,0.59903204,-0.4369334,-0.5335572,-0.49242222,-0.90506005,0.78425515,0.012328029,0.71873105,-0.3016864,0.76534665,0.3107903,-0.033233643,-0.5017556,-0.6505711,-0.68616843,0.43601704,0.8252932,0.90565944,-0.53583884,0.117555976,-0.57123494,-0.12595415,-0.71578634,-0.74638987,0.20536649,-0.2949834,-0.16182709,-0.41145825,-0.5736042,0.4855982,0.22109509,-0.89096355,0.12201846,0.48926818,0.11793423,0.07162428,-0.30661058,0.65713453,0.011079192,0.7019726,0.7430372,-0.4621868,-0.5048274,0.009478688,0.75391936,0.68731785,-0.26397014,0.22575927,-0.2729752,0.9447782,0.8961519,0.011144161,0.7317381,-0.37757313,0.5086689,-0.12508118,-0.035972357,0.527881,-0.36318314,0.3498001,0.18276179,0.20336795,0.185745,0.007154584,-0.95175207,0.9315262,0.07236111,-0.91375494,0.069800615,-0.6866547,-0.10195255,0.5590147,0.63743687,-0.2554958,0.2556857,0.1135515,-0.57045686,0.35691392,0.4400475,-0.40239632,-0.27728724,-0.81100214,-0.12102139,0.7328193,0.55729675,0.018718958,-0.52203035,-0.2635665,-0.9858109,-0.2635758,-0.15508783,0.24690652,-0.87712514,-0.06969631,0.26361036,0.5517025,0.14193296,0.8669697,0.6987207,0.49846697,0.53141236,-0.08405697,-0.9177644,-0.89400125,-0.8215778,0.11350715,-0.15031528,0.28057933,0.055780888,-0.30645669,0.01640606,-0.78037536,-0.89219594,0.5169045,0.020317554,0.11270356,0.79266965,0.17422187,-0.22640634,-0.7745559,0.29890132,0.66857207,-0.3161881,0.0016554594,0.33228195,-0.16573918,0.30396903,-0.3986442,-0.21694672,0.62741494,0.38507068,0.49674237,0.18199372,0.025707483,0.7257539,0.095201015,-0.80548155,-0.8278079,-0.21208572,0.60015476,-0.55279064,-0.45302844,0.6790507,0.7401136,-0.39969444,-0.9133935,-0.9491818,0.9081656,0.048037767,-0.68765414,0.645074,0.5040283,-0.8720572,-0.5267173,0.7058039,0.7356373,0.8383081,0.5394591,0.99622726,-0.774737,0.29383254,-0.5458864,0.59986985,-0.08420563,0.39382935,0.5162437,0.58348083,0.95679307,0.2414825,0.038288236,0.6556151,-0.53083277,0.949692,0.7140814,-0.47897446,-0.124956965,0.71804273,0.4256395,0.21747434,0.40007293,0.99180067,0.7813685,-0.5058098,-0.49528062,-0.4507804,-0.43822098,0.5722486,0.5059439,-0.65428555,0.7818172,0.39512706,0.16941452,-0.38322175,-0.6271856,-0.13261914,-0.72572374,-0.74373424,0.93439364,0.3817861,0.298414,0.8994843,0.78127325,0.09769189,0.6939156,0.9143168,-0.93313634,-0.44078672,0.5033423,0.0074801445,-0.65881646,0.09105849,-0.8781427,-0.8375951,-0.9874973,-0.60177636,-0.8160161,-0.1782738,0.12909377,0.49577808,0.22368693,-0.30108368,0.25959897,0.7350166,0.8308252,-0.65571666,-0.61086345,0.6571351,-0.5547575,8.2206726E-4,0.30596197,0.73631656,-0.8374438,0.67184913,-0.6459253,0.96794486,0.47503197,0.397897,0.37572038,-0.43186426,0.7699853,0.124105334,0.65059304,-0.2123816,-0.2786082,0.2567351,0.4232787,0.6722735,-0.9664856,-0.009930253,-0.25407207,-0.6534413,-0.044672012,0.070765495,0.6793734,0.5331824,0.739529,0.09979093,-0.99574494,0.1874615,0.95943224,0.4028622,0.38675296,0.60884404,-0.9582372,0.48536265,0.09508455,0.6423627,-0.7247815,-0.909966,-0.17191935,-0.2971822,0.19689274,-0.23562586,-0.267761,0.6040362,0.9556806,0.89506793,-0.7337619,-0.5293629,-0.1506679,0.5605562,0.020820975,-0.5773846,0.60682225,0.8778944,-0.0027132034,0.9822446,-0.45161402,-0.72947836,0.05090046,0.1430757,0.9413655,0.15982962,0.35892153,0.30317616,-0.3483621,0.7921387,0.8004854,0.49647868,-0.43020308,0.4667431,-0.75767124,-0.08928573,0.80164385,-0.550781,-0.103336334,-0.9323504,-0.181638,-0.13741195,-0.5770607,0.42725086,0.7587049,-0.7211602,0.3326336,0.88421965,-0.009426475,0.42262292,0.8556466,-0.36838567,0.5307015,0.09176266,-0.15033126,0.3067336,0.6782522,-0.7539556,-0.02736497,-0.9998704,-0.5383303,0.27304924,-0.63311005,0.54678917,0.48007035,-0.82935905,0.97704494,0.13269758,-0.77599645,-0.84088016,-0.421952,0.39993656,0.8000227,0.82746625,-0.8567226,0.3351953,-0.49512506,-0.99086607,0.63334346,-0.14766192,0.27520263,-0.7385727,0.2148192,0.35801375,-0.4143405,-0.91727996,0.6856456,-0.89505315,0.8217131,0.24406385,0.13304853,-0.1684345,-0.016134739,0.14271772,-0.9957073,0.25623465,0.2894975,-0.3823037,-0.46444142,-0.70689,0.86026037,0.82073784,0.34599125,-0.6057385,-0.71867406,-0.46449852,-0.26988542,-0.13473833,0.98960114,-0.28501463,-0.6788473,-0.8288543,-0.18187201,0.2999723,-0.8195616,0.62505853,0.7515658,0.83393335,0.60534203,0.06775987,-0.7776866,-0.34739578,-0.06740737,-0.5065124,0.8191987,-0.9439267,-0.98066616,0.5037432,-0.15732074,0.8305435,-0.13418603,0.07887709,-0.99462295,0.64511013,0.7376034,-0.2923156,0.058950543,0.48132372,-0.83077526,0.32358158,-0.9264873,-0.45838964,-0.8160914,0.83459985,0.3809582,-0.38529468,0.025928497,-0.9680971,-0.77309406,-0.85374725,0.5339433,-0.79740894,0.39690924,-0.82897437,-0.87142646,-0.60179675,0.4618709,-0.38353074,0.16887593,-0.2821641,-0.17284119,0.49835706,-0.6444515,0.50768375,0.002954483,0.31740928,0.9099102,0.5668324,0.0043444633,0.05616021,-0.29545045,0.44831872,0.6342758,-2.0587444E-4,0.41283667,-0.9229219,0.7276983,-0.9989873,-0.5896622,0.15094733,0.8992151,-0.47862196,0.71286964,0.282135,-0.3711939,-0.03962016,-0.93391466,-0.634454,-0.13861692,-0.514637,-0.41610658,-0.82570374,0.9380325,-0.73670983,0.5227834,-0.71478665,-0.59699285,0.03826332,-0.8874302,-0.11216581,-0.14471138,-0.82783556,0.3587669,0.24542308,0.88257253,0.2513517,-0.31341434,-0.87192273,-0.39423263,-0.7520603,0.79148614,-0.34539163,-0.6182407,0.0039613247,-0.5423502,-0.9536717,0.7007301,0.8810197,-0.95376444,0.8531617,-0.25757885,0.09355819,0.26367402,0.4579004,-0.07462525,0.23347533,-0.59837294,-0.04855144,0.1275518,0.41663945,0.980903,0.81031597,0.14027512,0.363307,-0.600827,0.86344826,0.93589234,-0.5038477,0.3255856,0.6309761,-0.8901708,-0.86052835,-0.9655758,0.73115087,-0.44196904,-0.6247287,-0.76389265,-0.6816231,0.26061165,-0.29322827,-0.8603177,0.7258564,0.6825539,-0.045989037,0.14033282,0.08821082,0.5244745,0.7225871,0.6233903,0.81603575,-0.35599196,0.21428967,0.6492356,0.6454611,0.23569691,0.36282885,-0.25112414,-0.49256933,0.6501361,0.46070528,0.5167184,0.7258599,-0.57156456,-0.81762147,0.21520543,0.7895839,0.63405526,-0.3015797,-0.93314135,0.034846783,-0.17981803,0.31567085,0.44548476,0.5640073,0.94774365,-0.42380333,-0.28636086,0.30943215,0.26888502,-0.7840326,0.19301546,-0.31728935,-0.8535882,0.941759,0.6845528,-0.7618685,-0.44414973,-0.8427417,0.34330118,0.39183128,-0.2909671,0.9883096,0.60830045,-0.20359266,-0.026518822,-0.9393786,-0.3800292,-0.54638255,-0.30148935,0.21515393,0.23812485,0.18981683,0.38638103,0.85725784,-0.20473444,0.5110353,-0.9143319,0.9820404,-0.7438446,-0.95567,0.18628514,0.56331384,0.96081614,0.36278725,-0.2555585,-0.6940434,-0.15953314,-0.25694847,-0.12425029,0.51698864,0.4320122,0.73254025,-0.028631806,0.7307651,0.4753785,0.880841,-0.10752714,-0.18367791,0.93934524,0.84468246,0.7906599,-0.405797,0.3468542,0.69309366,0.7255795,-0.10356057,-0.09731424,0.65994143,0.27685642,0.8234098,0.5886893,0.012211442,0.37715387,0.39793003,-0.21046877,-0.022290349,0.66025615,0.50928855,-0.3685032,0.815055,-0.21324468,-0.9965799,-0.17415988,0.7779435,0.07602644,-0.05835426,-0.04466319,-0.2530614,0.96360874,-0.2427057,-0.34185243,-0.845986,0.4760958,-0.46880198,0.65815926,0.14249027,0.26061225,-0.46609008,-0.04176712,0.97890425,-0.5563636,0.10117006,0.42605746,0.010968804,0.47625744,-0.21746123,-0.5833101,0.050664783,0.433146,-0.59964585,0.7276604,-0.7866063,0.28501296,0.45266294,0.08932841,0.8790525,-0.69515586,-0.62886274,-0.64618146,0.6311084,-0.1263274,-0.9088614,-0.35958767,0.5037955,0.9302975,0.70014656,-0.2149353,-0.48983455,-0.3993758,0.5031369,-0.33067942,0.7759409,0.1168952,-0.5141983,0.2931621,0.36318564,-0.0030517578,-0.9183427,0.012290597,0.38725972,0.13199663,0.47620416,0.16937935,-0.1798904,0.49270666,0.93580365,-0.3215109,0.5986961,-0.296723,0.26243508,-0.9790423,0.6415262,0.8787384,0.28589845,-0.090275645,0.09736693,-0.46999967,0.05647266,0.68856835,0.74302745,-0.2962706,0.24390805,-0.2542987,0.7026303,0.06065154,-0.7604195,0.011380792,-0.47369516,-0.44172335,0.06053531,-0.5682087,0.74525964,-0.42443597,-0.2638389,0.40269637,0.07246828,0.5121231,-0.80426633,0.02387166,0.8597746,-0.5411215,-0.5502939,0.9979254,-0.1746651,-0.15231085,-0.84272003,0.62486553,-0.34150553,-0.047621727,0.4916184,-0.0019847155,0.2771988,0.672482,0.95244133,-0.16980827,0.29689848,0.65444183,0.74032044,-0.24473274,-0.13314009,-0.0050843954,-0.78934443,-0.8355874,-0.7066102,-0.43943644,-0.6127968,-0.8651949,-0.19054389,0.4125297,-0.43945956,-0.4410559,0.13257682,0.5375918,0.55761266,-0.26621306,-0.10009301,0.688547,-0.31756675,-0.74922585,0.1597029,-0.21796179,-0.16079056,0.6517831,0.46654427,-0.81784,0.60805714,0.87470996,0.06486714,-0.96648335,0.60667896,-0.32043362,-0.40123177,0.34989715,-0.6982032,0.97675383,0.8591986,-0.90242946,0.4009099,-0.9901079,-0.46766114,0.34986448,0.33346117,0.6937871,-0.33884025,-0.4221468,0.30239904,0.8726729,0.6481689,-0.84238374,-0.45970285,-0.07518232,0.83967113,-0.37417257,0.23278105,0.85828125,-0.07473898,-0.45813847,-0.02358663,0.9616258,-0.8982363,-0.013147593,0.42502427,0.11654019,0.8856875,-0.24983788,0.16065931,-0.74827886,-0.034015656,0.65473366,-0.07802987,0.10529196,0.079283714,0.8192377,0.65939856,0.7341002,0.72879004,-0.9574907,0.8945401,-0.8887383,0.9779887,-0.9485605,-0.62149477,-0.981624,-0.4903109,0.73545253,0.8150685,0.37726128,-0.21546781,-0.6115866,0.58930254,-0.42957664,-0.7481046,0.61297286,-0.9221343,-0.6563927,-0.8925668,0.16492164,-0.3239473,0.02276802,-0.3412161,0.40930104,0.5282531,0.2479546,0.7140429,0.040247917,0.8679999,-0.25447905,0.9624487,-0.3714546,-0.457047,0.12657535,-0.3543749,-0.21843505,0.8068558,-0.1062603,-0.8508216,0.1379124,0.7988584,-0.87855875,0.86324096,-0.9869075,0.70101166,-0.7103239,0.8076837,0.73358536,0.6738467,-0.07870531,0.2904607,-0.95316875,0.030646801,-0.2965511,-0.8805759,0.50695837,0.34244168,0.15122473,0.6287006,-0.52489126,-0.7067021,0.36325574,0.6763978,0.9346273,0.06077087,-0.4259132,0.6432406,0.33212757,0.11734331,-0.14368176,-0.20972586,0.27374244,-0.12059617,-0.92155933,0.7527497,-0.67884076,0.22124505,-0.02566576,0.7477634,0.06502795,-0.92070055,0.6015017,-0.29759204,0.8559785,0.159284,0.5122845,-0.92936516,0.32897973,0.9952283,0.37193418,0.860482,0.47958887,0.5699142,0.5470239,0.25480986,-0.6135446,-0.4273206,0.15614069,0.25047517,0.6002418,0.73075235,0.30216324,0.6914706,0.14307165,0.9984009,0.55303,-0.18426073,-0.57579625,-0.37539148,0.3483256,0.514143,-0.85247016,0.051362872,0.80607665,0.61070156,0.46668124,-0.5661446,-0.4070598,-0.03077364,-0.23280835,0.4400729,-0.9121927,-0.37737525,-0.9132353,0.40582585,0.28131413,-0.4265399,0.41993105,-0.67618406,-0.23931372,0.7500843,-0.88079274,-0.6301851,-0.93919826,-0.1996715,0.9594923,0.45952892,-0.2703085,-0.9008875,0.8653048,-0.16334283,0.052467227,0.32842755,-0.96771586,-0.027337313,0.675706,0.20522547,-0.5428997,-0.4252615] } } }"; // Replace with your JSON payload
//            String jsonPayload = "{ \"findOneAndDelete\": { \"sort\": { \"$vector\": [0.1, 0.15, 0.3, 0.12, 0.05]} } }"; // Replace with your JSON payload
//            String jsonPayload = "{ \"findOneAndDelete\": { \"sort\": { \"$vector\": [0.1, 0.15, 0.3, 0.12, 0.05]} } }"; // Replace with your JSON payload

//            String jsonPayload = "{ \"deleteOne\": { \"filter\": { \"name\": \"Logic Layers\"} } }"; // Replace with your JSON payload
//            String jsonPayload = "{ \"findOneAndDelete\": { \"filter\": { \"name\": \"Logic Layers\"}, {\"projection\" : {\"name\" : 1 } } } }"; // Replace with your JSON payload
            String jsonPayload = "{ \"findOneAndDelete\": { \"filter\": { \"name\": \"Logic Layers\"} } }"; // Replace with your JSON payload

//            String jsonPayload = "{ \"findOneAndUpdate\": " +
//                    "{ " +
//                    "\"sort\": { \"$vector\": [0.1, 0.15, 0.3, 0.12, 0.05]} , " +
//                    "\"update\" : { \"$set\" : {\"status\": \"updated\" } }" +
//                    "} " +
//                    "}"; // Replace with your JSON payload

            try {
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set up the connection for a POST request
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("x-cassandra-token","90feffa9-6870-42fa-ba01-910cf3d640bd");
                connection.setDoOutput(true);

                // Send the JSON payload
                try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                    outputStream.writeBytes(jsonPayload);
                    outputStream.flush();
                }

                // Get the response from the API
                int responseCode = connection.getResponseCode();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Response Code: " + responseCode);
                System.out.println("Response Body: " + response.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
