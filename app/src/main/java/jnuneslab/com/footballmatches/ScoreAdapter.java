package jnuneslab.com.footballmatches;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by Walter on 23/11/2015.
 */
public class ScoreAdapter  extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    public static final int COL_HOME = 6;
    public static final int COL_AWAY = 7;
    public static final int COL_HOME_GOALS = 8;
    public static final int COL_AWAY_GOALS = 9;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 1;
    public static final int COL_MATCHTIME = 4;
    public double detail_match_id = 0;
    private String FOOTBALL_SCORES_HASHTAG = "#Football_Scores";

    private Cursor mCursor;
    private Context mContext;
    private LayoutInflater  mInflater;
    // private OnScoreItemClickListener mListener = OnScoreItemClickListener.DUMMY;

    //public ScoresAdapter setListener(OnScoreItemClickListener listener) {
    //  mListener = listener;
    // return this;
    // }

    public ScoreAdapter(Fragment fragment)
    {
        mInflater = LayoutInflater.from(fragment.getActivity());
        mContext = fragment.getActivity();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        Log.v("teste" , "test getitemcount");
        return mCursor != null ? mCursor.getCount() : 0;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("teste" , "test createviewholder");

        return new ViewHolder(mInflater.inflate(R.layout.scores_list_item, parent, false));
       /* View mItem = LayoutInflater.from(mContext).inflate(R.layout.scores_list_item, parent, false);
        ViewHolder mHolder = new ViewHolder(mItem);
        mItem.setTag(mHolder);
        Log.v(FetchScoreTask.LOG_TAG, "new View inflated");
        return mItem;*/
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.v("teste" , "test bindviewholder");
        //final ViewHolder mHolder = (ViewHolder) view.getTag();
        mCursor.moveToPosition(position);
        holder.home_name.setText(mCursor.getString(COL_HOME));
      //  Log.v(FetchScoreTask.LOG_TAG, "bind View inflated 1" + mCursor.getString(1) + " >2<" + mCursor.getString(2) + " >3<" + mCursor.getString(3) + " >4<" + mCursor.getString(4) + " >5<" + mCursor.getString(5) + " >6<" + mCursor.getString(6) + " >7<" + mCursor.getString(7) + " >8<" + mCursor.getString(8) + " >9<" + mCursor.getString(9)) ;
        holder.away_name.setText(mCursor.getString(COL_AWAY));
        holder.date.setText(mCursor.getString(COL_MATCHTIME));
        holder.score.setText(Util.getScores(mCursor.getInt(COL_HOME_GOALS),mCursor.getInt(COL_AWAY_GOALS)));
        holder.match_id = mCursor.getDouble(COL_ID);
        holder.home_crest.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(COL_HOME)));
        holder.away_crest.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(COL_AWAY)
        ));
        //Log.v(FetchScoreTask.LOG_TAG,mHolder.home_name.getText() + " Vs. " + mHolder.away_name.getText() +" id " + String.valueOf(mHolder.match_id));
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(detail_match_id));
        LayoutInflater vi = (LayoutInflater) mContext.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.fragment_main, null);
        // ViewGroup container = (ViewGroup) view.findViewById(R.id.details_fragment_container);
        //  if(holder.match_id == detail_match_id)
        {
            //Log.v(FetchScoreTask.LOG_TAG,"will insert extraView");

            //    container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
            //             , ViewGroup.LayoutParams.MATCH_PARENT));
            // TextView match_day = (TextView) v.findViewById(R.id.matchday_textview);
            // match_day.setText(Utilies.getMatchDay(cursor.getInt(COL_MATCHDAY),
            //         cursor.getInt(COL_LEAGUE)));
            // TextView league = (TextView) v.findViewById(R.id.);
            // league.setText(Utilies.getLeague(cursor.getInt(COL_LEAGUE)));
            //   Button share_button = (Button) v.findViewById(R.id.share_button);
            //   share_button.setOnClickListener(new View.OnClickListener() {
            //      @Override
            //      public void onClick(View v)
            //        {
            //add Share Action
            //           context.startActivity(createShareForecastIntent(mHolder.home_name.getText()+" "
            //                  +mHolder.score.getText()+" "+mHolder.away_name.getText() + " "));
            //       }
            //   });
        }
        //  else
        //   {
        //      Log.v(FetchScoreTask.LOG_TAG, "remove views");
        //     container.removeAllViews();
        //  }
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView home_name;
        public TextView away_name;
        public TextView score;
        public TextView date;
        public ImageView home_crest;
        public ImageView away_crest;
        public double match_id;
        public ViewHolder(View view)
        {   super(view);
            home_name = (TextView) view.findViewById(R.id.home_name);
            away_name = (TextView) view.findViewById(R.id.away_name);
            score     = (TextView) view.findViewById(R.id.score_textview);
            date      = (TextView) view.findViewById(R.id.data_textview);
            home_crest = (ImageView) view.findViewById(R.id.home_crest);
            away_crest = (ImageView) view.findViewById(R.id.away_crest);
        }
    }
}