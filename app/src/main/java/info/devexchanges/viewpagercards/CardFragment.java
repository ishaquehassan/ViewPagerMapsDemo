package info.devexchanges.viewpagercards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CardFragment extends Fragment {

    private CardView cardView;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);

        cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        final TextView title = (TextView) view.findViewById(R.id.title);
        final TextView details = (TextView) view.findViewById(R.id.details);
        Button button = (Button)view.findViewById(R.id.button);

        title.setText(getArguments().getString("title"));
        details.setText(getArguments().getString("descp"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("descp",details.getText().toString());
                intent.putExtra("position",getArguments().getParcelable("position"));
                startActivity(intent);
            }
        });

        return view;
    }

    public CardView getCardView() {
        return cardView;
    }
}
