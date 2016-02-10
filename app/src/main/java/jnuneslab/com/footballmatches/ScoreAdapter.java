package jnuneslab.com.footballmatches;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Walter on 23/11/2015.
 */
public class ScoreAdapter  extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    // Map columns
    public static final int COL_ID = 1;
    public static final int COL_MATCHDAY = 2;
    public static final int COL_LEAGUE = 3;
    public static final int COL_MATCHSTARTTIME = 4;
    public static final int COL_MATCHLENGTH = 5;
    public static final int COL_HOME = 6;
    public static final int COL_AWAY = 7;
    public static final int COL_HOME_GOALS = 8;
    public static final int COL_AWAY_GOALS = 9;
    public static final int COL_LEAGUE_NAME = 11;

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
        Log.v("teste", "test bindviewholder");
        //final ViewHolder mHolder = (ViewHolder) view.getTag();
        mCursor.moveToPosition(position);
        holder.homeNameTxtView.setText(mCursor.getString(COL_HOME));
        Log.v(FetchScoreTask.LOG_TAG, "bind View inflated 1><" + mCursor.getString(10) + " >2<" + mCursor.getString(2) + " >3<" + mCursor.getString(3) + " >4<" + mCursor.getString(4) + " >5<" + mCursor.getString(5) + " >6<" + mCursor.getString(6) + " >7<" + mCursor.getString(7) + " >8<" + mCursor.getString(8) + " >9<" + mCursor.getString(9) ) ;
        holder.awayNameTxtView.setText(mCursor.getString(COL_AWAY));
        holder.leagueNameTxtView.setText(mCursor.getString(COL_LEAGUE_NAME));
        holder.matchTimeTxtView.setText(mCursor.getString(COL_MATCHSTARTTIME));
        Log.v(FetchScoreTask.LOG_TAG, "bind View inflated 2" + mCursor.getInt(COL_HOME_GOALS) + " " + mCursor.getInt(COL_AWAY_GOALS));
        Log.v(FetchScoreTask.LOG_TAG, "bind View inflated 3" + Util.getScores(mCursor.getInt(COL_HOME_GOALS), mCursor.getInt(COL_AWAY_GOALS)));
               holder.scoreTxtView.setText(Util.getScores(mCursor.getInt(COL_HOME_GOALS), mCursor.getInt(COL_AWAY_GOALS)));
        holder.match_id = mCursor.getDouble(COL_ID);
        holder.homeFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
                mCursor.getString(COL_HOME)));
        holder.awayFlagImgView.setImageResource(Util.getTeamCrestByTeamName(
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
        protected TextView homeNameTxtView;
        protected TextView awayNameTxtView;
        protected TextView leagueNameTxtView;
        protected TextView scoreTxtView;
        protected TextView matchTimeTxtView;
        protected ImageView homeFlagImgView;
        protected ImageView awayFlagImgView;
        protected double match_id;

        public ViewHolder(View view) {
            super(view);
            homeNameTxtView = (TextView) view.findViewById(R.id.home_name);
            awayNameTxtView = (TextView) view.findViewById(R.id.away_name);
            leagueNameTxtView = (TextView) view.findViewById(R.id.league_textview);
            scoreTxtView = (TextView) view.findViewById(R.id.score_item_score);
            matchTimeTxtView = (TextView) view.findViewById(R.id.matchtime_textview);
            homeFlagImgView = (ImageView) view.findViewById(R.id.home_crest);
            awayFlagImgView = (ImageView) view.findViewById(R.id.away_crest);
        }
    }
}