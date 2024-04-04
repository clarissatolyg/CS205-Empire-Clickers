import com.supabase.SupabaseClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.time.Instant;

public class HighScore {

    private SupabaseClient supabase;

    public HighScore(SupabaseClient supabaseClient) {
        this.supabase = supabaseClient;
    }

    public void insertStatus(String userName, int score, int foodFactoryCount, int clothesFactoryCount, int paperFactoryCount, int electronicsFactoryCount, int carFactoryCount) {
        Map<String, Object> statusData = new HashMap<>();
        statusData.put("user_name", userName);
        statusData.put("score", score);
        statusData.put("food_factory_count", foodFactoryCount);
        statusData.put("clothes_factory_count", clothesFactoryCount);
        statusData.put("paper_factory_count", paperFactoryCount);
        statusData.put("electronics_factory_count", electronicsFactoryCount);
        statusData.put("car_factory_count", carFactoryCount);

        // Automatically generate the current timestamp in ISO 8601 format
        String timestamp = Instant.now().toString();
        statusData.put("created_at", timestamp);

        // Insert the status into the "status" table
        supabase.from("status")
                .insert(statusData)
                .executeAsync(response -> {
                    System.out.println("Status inserted successfully.");
                }, error -> {
                    System.out.println("Error inserting status: " + error.getMessage());
                });
    }

    public void fetchTopScores(int limit, Callback<List<StatusEntry>> callback) {
        supabase.from("status")
                .select("*")
                .order("score", false)
                .limit(limit)
                .executeAsync(response -> {
                    List<StatusEntry> statuses = new ArrayList<>();
                    try {
                        JSONArray results = new JSONArray(response.getBody());
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            StatusEntry entry = new StatusEntry(
                                    result.getLong("entry_id"),
                                    result.getString("user_name"),
                                    result.getInt("score"),
                                    result.getString("created_at"),
                                    result.getInt("food_factory_count"),
                                    result.getInt("clothes_factory_count"),
                                    result.getInt("paper_factory_count"),
                                    result.getInt("electronics_factory_count"),
                                    result.getInt("car_factory_count")
                            );
                            statuses.add(entry);
                        }
                        callback.onSuccess(statuses);
                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                }, error -> {
                    callback.onFailure(new Exception("Failed to fetch top statuses: " + error.getMessage()));
                });
    }

    // Callback interface for async responses
    public interface Callback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    // Status entry class to hold fetched statuses
    public static class StatusEntry {
        public long entryId;
        public String userName;
        public int score;
        public int foodFactoryCount;
        public int clothesFactoryCount;
        public int paperFactoryCount;
        public int electronicsFactoryCount;
        public int carFactoryCount;

        public StatusEntry(long entryId, String userName, int score, int foodFactoryCount, int clothesFactoryCount, int paperFactoryCount, int electronicsFactoryCount, int carFactoryCount) {
            this.entryId = entryId;
            this.userName = userName;
            this.score = score;
            this.foodFactoryCount = foodFactoryCount;
            this.clothesFactoryCount = clothesFactoryCount;
            this.paperFactoryCount = paperFactoryCount;
            this.electronicsFactoryCount = electronicsFactoryCount;
            this.carFactoryCount = carFactoryCount;
        }

        @Override
        public String toString() {
            return "StatusEntry{" +
                    "entryId=" + entryId +
                    ", userName='" + userName + '\'' +
                    ", score=" + score +
                    ", created_at='" + created_at +
                    ", foodFactoryCount=" + foodFactoryCount +
                    ", clothesFactoryCount=" + clothesFactoryCount +
                    ", paperFactoryCount=" + paperFactoryCount +
                    ", electronicsFactoryCount=" + electronicsFactoryCount +
                    ", carFactoryCount=" + carFactoryCount +
                    '}';
        }
    }
}
