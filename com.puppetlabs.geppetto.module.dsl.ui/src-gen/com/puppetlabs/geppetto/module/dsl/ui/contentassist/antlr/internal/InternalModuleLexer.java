package com.puppetlabs.geppetto.module.dsl.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalModuleLexer extends Lexer {
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int RULE_LONG=6;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int EOF=-1;
    public static final int RULE_DOUBLE=5;
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_STRING=4;
    public static final int T__16=16;
    public static final int T__33=33;
    public static final int T__15=15;
    public static final int T__34=34;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int RULE_EXPONENT=8;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int RULE_WS=9;
    public static final int RULE_DIGIT=7;

    // delegates
    // delegators

    public InternalModuleLexer() {;} 
    public InternalModuleLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalModuleLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g"; }

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:11:7: ( 'null' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:11:9: 'null'
            {
            match("null"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:12:7: ( '\"author\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:12:9: '\"author\"'
            {
            match("\"author\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:13:7: ( '\"dependencies\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:13:9: '\"dependencies\"'
            {
            match("\"dependencies\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:14:7: ( '\"description\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:14:9: '\"description\"'
            {
            match("\"description\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:15:7: ( '\"license\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:15:9: '\"license\"'
            {
            match("\"license\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:16:7: ( '\"name\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:16:9: '\"name\"'
            {
            match("\"name\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:17:7: ( '\"operatingsystem\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:17:9: '\"operatingsystem\"'
            {
            match("\"operatingsystem\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:18:7: ( '\"operatingsystem_support\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:18:9: '\"operatingsystem_support\"'
            {
            match("\"operatingsystem_support\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:19:7: ( '\"operatingsystemrelease\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:19:9: '\"operatingsystemrelease\"'
            {
            match("\"operatingsystemrelease\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:20:7: ( '\"parameters\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:20:9: '\"parameters\"'
            {
            match("\"parameters\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:21:7: ( '\"project_page\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:21:9: '\"project_page\"'
            {
            match("\"project_page\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:22:7: ( '\"requirements\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:22:9: '\"requirements\"'
            {
            match("\"requirements\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:23:7: ( '\"source\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:23:9: '\"source\"'
            {
            match("\"source\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:24:7: ( '\"summary\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:24:9: '\"summary\"'
            {
            match("\"summary\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:25:7: ( '\"tags\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:25:9: '\"tags\"'
            {
            match("\"tags\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:26:7: ( '\"version\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:26:9: '\"version\"'
            {
            match("\"version\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:27:7: ( '\"version_requirement\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:27:9: '\"version_requirement\"'
            {
            match("\"version_requirement\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:28:7: ( 'true' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:28:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:29:7: ( 'false' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:29:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:30:7: ( '{' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:30:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:31:7: ( '}' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:31:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:32:7: ( ',' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:32:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:33:7: ( ':' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:33:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:34:7: ( '[' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:34:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:35:7: ( ']' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:35:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "RULE_DIGIT"
    public final void mRULE_DIGIT() throws RecognitionException {
        try {
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8095:21: ( '0' .. '9' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8095:23: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DIGIT"

    // $ANTLR start "RULE_EXPONENT"
    public final void mRULE_EXPONENT() throws RecognitionException {
        try {
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8097:24: ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_DIGIT )+ )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8097:26: ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_DIGIT )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8097:36: ( '+' | '-' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='+'||LA1_0=='-') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8097:47: ( RULE_DIGIT )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8097:47: RULE_DIGIT
            	    {
            	    mRULE_DIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_EXPONENT"

    // $ANTLR start "RULE_LONG"
    public final void mRULE_LONG() throws RecognitionException {
        try {
            int _type = RULE_LONG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8099:11: ( ( '-' )? ( RULE_DIGIT )+ )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8099:13: ( '-' )? ( RULE_DIGIT )+
            {
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8099:13: ( '-' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8099:13: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8099:18: ( RULE_DIGIT )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8099:18: RULE_DIGIT
            	    {
            	    mRULE_DIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_LONG"

    // $ANTLR start "RULE_DOUBLE"
    public final void mRULE_DOUBLE() throws RecognitionException {
        try {
            int _type = RULE_DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:13: ( ( ( '-' )? ( RULE_DIGIT )+ RULE_EXPONENT | ( '-' )? ( RULE_DIGIT )+ '.' ( RULE_DIGIT )+ ( RULE_EXPONENT )? ) )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:15: ( ( '-' )? ( RULE_DIGIT )+ RULE_EXPONENT | ( '-' )? ( RULE_DIGIT )+ '.' ( RULE_DIGIT )+ ( RULE_EXPONENT )? )
            {
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:15: ( ( '-' )? ( RULE_DIGIT )+ RULE_EXPONENT | ( '-' )? ( RULE_DIGIT )+ '.' ( RULE_DIGIT )+ ( RULE_EXPONENT )? )
            int alt11=2;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:16: ( '-' )? ( RULE_DIGIT )+ RULE_EXPONENT
                    {
                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:16: ( '-' )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='-') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:16: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }

                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:21: ( RULE_DIGIT )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:21: RULE_DIGIT
                    	    {
                    	    mRULE_DIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);

                    mRULE_EXPONENT(); 

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:47: ( '-' )? ( RULE_DIGIT )+ '.' ( RULE_DIGIT )+ ( RULE_EXPONENT )?
                    {
                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:47: ( '-' )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='-') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:47: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }

                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:52: ( RULE_DIGIT )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:52: RULE_DIGIT
                    	    {
                    	    mRULE_DIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);

                    match('.'); 
                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:68: ( RULE_DIGIT )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:68: RULE_DIGIT
                    	    {
                    	    mRULE_DIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:80: ( RULE_EXPONENT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='E'||LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8101:80: RULE_EXPONENT
                            {
                            mRULE_EXPONENT(); 

                            }
                            break;

                    }


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_DOUBLE"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8103:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8103:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8103:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='\\') ) {
                    alt12=1;
                }
                else if ( ((LA12_0>='\u0000' && LA12_0<='!')||(LA12_0>='#' && LA12_0<='[')||(LA12_0>=']' && LA12_0<='\uFFFF')) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8103:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8103:65: ~ ( ( '\\\\' | '\"' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8105:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8105:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:8105:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='\t' && LA13_0<='\n')||LA13_0=='\r'||LA13_0==' ') ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    public void mTokens() throws RecognitionException {
        // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:8: ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | RULE_LONG | RULE_DOUBLE | RULE_STRING | RULE_WS )
        int alt14=29;
        alt14 = dfa14.predict(input);
        switch (alt14) {
            case 1 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:10: T__10
                {
                mT__10(); 

                }
                break;
            case 2 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:16: T__11
                {
                mT__11(); 

                }
                break;
            case 3 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:22: T__12
                {
                mT__12(); 

                }
                break;
            case 4 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:28: T__13
                {
                mT__13(); 

                }
                break;
            case 5 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:34: T__14
                {
                mT__14(); 

                }
                break;
            case 6 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:40: T__15
                {
                mT__15(); 

                }
                break;
            case 7 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:46: T__16
                {
                mT__16(); 

                }
                break;
            case 8 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:52: T__17
                {
                mT__17(); 

                }
                break;
            case 9 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:58: T__18
                {
                mT__18(); 

                }
                break;
            case 10 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:64: T__19
                {
                mT__19(); 

                }
                break;
            case 11 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:70: T__20
                {
                mT__20(); 

                }
                break;
            case 12 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:76: T__21
                {
                mT__21(); 

                }
                break;
            case 13 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:82: T__22
                {
                mT__22(); 

                }
                break;
            case 14 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:88: T__23
                {
                mT__23(); 

                }
                break;
            case 15 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:94: T__24
                {
                mT__24(); 

                }
                break;
            case 16 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:100: T__25
                {
                mT__25(); 

                }
                break;
            case 17 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:106: T__26
                {
                mT__26(); 

                }
                break;
            case 18 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:112: T__27
                {
                mT__27(); 

                }
                break;
            case 19 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:118: T__28
                {
                mT__28(); 

                }
                break;
            case 20 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:124: T__29
                {
                mT__29(); 

                }
                break;
            case 21 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:130: T__30
                {
                mT__30(); 

                }
                break;
            case 22 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:136: T__31
                {
                mT__31(); 

                }
                break;
            case 23 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:142: T__32
                {
                mT__32(); 

                }
                break;
            case 24 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:148: T__33
                {
                mT__33(); 

                }
                break;
            case 25 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:154: T__34
                {
                mT__34(); 

                }
                break;
            case 26 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:160: RULE_LONG
                {
                mRULE_LONG(); 

                }
                break;
            case 27 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:170: RULE_DOUBLE
                {
                mRULE_DOUBLE(); 

                }
                break;
            case 28 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:182: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 29 :
                // ../com.puppetlabs.geppetto.module.dsl.ui/src-gen/com/puppetlabs/geppetto/module/dsl/ui/contentassist/antlr/internal/InternalModule.g:1:194: RULE_WS
                {
                mRULE_WS(); 

                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA11_eotS =
        "\5\uffff";
    static final String DFA11_eofS =
        "\5\uffff";
    static final String DFA11_minS =
        "\1\55\1\60\1\56\2\uffff";
    static final String DFA11_maxS =
        "\2\71\1\145\2\uffff";
    static final String DFA11_acceptS =
        "\3\uffff\1\2\1\1";
    static final String DFA11_specialS =
        "\5\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\1\2\uffff\12\2",
            "\12\2",
            "\1\3\1\uffff\12\2\13\uffff\1\4\37\uffff\1\4",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "8101:15: ( ( '-' )? ( RULE_DIGIT )+ RULE_EXPONENT | ( '-' )? ( RULE_DIGIT )+ '.' ( RULE_DIGIT )+ ( RULE_EXPONENT )? )";
        }
    }
    static final String DFA14_eotS =
        "\14\uffff\1\31\u00ac\uffff";
    static final String DFA14_eofS =
        "\u00b9\uffff";
    static final String DFA14_minS =
        "\1\11\1\uffff\1\0\10\uffff\1\60\1\56\1\uffff\12\0\3\uffff\52\0\1"+
        "\uffff\6\0\1\uffff\5\0\1\uffff\6\0\1\uffff\1\0\1\uffff\7\0\1\uffff"+
        "\2\0\1\uffff\2\0\1\uffff\4\0\3\uffff\3\0\1\uffff\4\0\2\uffff\13"+
        "\0\1\uffff\4\0\1\uffff\1\0\1\uffff\3\0\2\uffff\1\0\2\uffff\1\0\1"+
        "\uffff\1\0\2\uffff\3\0\1\uffff\3\0\1\uffff\13\0\1\uffff\2\0\1\uffff"+
        "\3\0\4\uffff";
    static final String DFA14_maxS =
        "\1\175\1\uffff\1\uffff\10\uffff\1\71\1\145\1\uffff\12\uffff\3\uffff"+
        "\52\uffff\1\uffff\6\uffff\1\uffff\5\uffff\1\uffff\6\uffff\1\uffff"+
        "\1\uffff\1\uffff\7\uffff\1\uffff\2\uffff\1\uffff\2\uffff\1\uffff"+
        "\4\uffff\3\uffff\3\uffff\1\uffff\4\uffff\2\uffff\13\uffff\1\uffff"+
        "\4\uffff\1\uffff\1\uffff\1\uffff\3\uffff\2\uffff\1\uffff\2\uffff"+
        "\1\uffff\1\uffff\1\uffff\2\uffff\3\uffff\1\uffff\3\uffff\1\uffff"+
        "\13\uffff\1\uffff\2\uffff\1\uffff\3\uffff\4\uffff";
    static final String DFA14_acceptS =
        "\1\uffff\1\1\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\2\uffff"+
        "\1\35\12\uffff\1\34\1\32\1\33\52\uffff\1\6\6\uffff\1\17\5\uffff"+
        "\1\6\6\uffff\1\17\1\uffff\1\2\7\uffff\1\15\2\uffff\1\2\2\uffff\1"+
        "\5\4\uffff\1\15\1\16\1\20\3\uffff\1\5\4\uffff\1\16\1\20\13\uffff"+
        "\1\12\4\uffff\1\4\1\uffff\1\12\3\uffff\1\3\1\4\1\uffff\1\13\1\14"+
        "\1\uffff\1\3\1\uffff\1\13\1\14\3\uffff\1\7\3\uffff\1\7\13\uffff"+
        "\1\21\2\uffff\1\21\3\uffff\1\11\1\10\1\11\1\10";
    static final String DFA14_specialS =
        "\2\uffff\1\75\13\uffff\1\103\1\12\1\60\1\43\1\166\1\3\1\163\1\110"+
        "\1\101\1\61\3\uffff\1\102\1\0\1\57\1\44\1\161\1\123\1\144\1\164"+
        "\1\u0080\1\73\1\100\1\42\1\27\1\32\1\22\1\65\1\46\1\162\1\130\1"+
        "\143\1\165\1\177\1\71\1\77\1\45\1\26\1\34\1\23\1\64\1\47\1\160\1"+
        "\131\1\152\1\167\1\u0082\1\72\1\76\1\37\1\31\1\33\1\24\1\63\1\uffff"+
        "\1\157\1\125\1\150\1\170\1\u0081\1\67\1\uffff\1\40\1\30\1\36\1\25"+
        "\1\62\1\uffff\1\151\1\127\1\146\1\171\1\74\1\70\1\uffff\1\50\1\uffff"+
        "\1\35\1\55\1\41\1\147\1\156\1\145\1\u0084\1\uffff\1\66\1\51\1\uffff"+
        "\1\15\1\54\1\uffff\1\142\1\155\1\172\1\u0083\3\uffff\1\104\1\16"+
        "\1\53\1\uffff\1\141\1\154\1\173\1\u0086\2\uffff\1\105\1\17\1\52"+
        "\1\137\1\153\1\174\1\u0085\1\120\1\20\1\56\1\133\1\uffff\1\175\1"+
        "\u0088\1\117\1\21\1\uffff\1\126\1\uffff\1\176\1\u0087\1\122\2\uffff"+
        "\1\124\2\uffff\1\121\1\uffff\1\112\2\uffff\1\113\1\116\1\111\1\uffff"+
        "\1\11\1\1\1\115\1\uffff\1\10\1\2\1\114\1\7\1\135\1\107\1\6\1\136"+
        "\1\106\1\5\1\132\1\uffff\1\4\1\134\1\uffff\1\14\1\140\1\13\4\uffff}>";
    static final String[] DFA14_transitionS = {
            "\2\15\2\uffff\1\15\22\uffff\1\15\1\uffff\1\2\11\uffff\1\7\1"+
            "\13\2\uffff\12\14\1\10\40\uffff\1\11\1\uffff\1\12\10\uffff\1"+
            "\4\7\uffff\1\1\5\uffff\1\3\6\uffff\1\5\1\uffff\1\6",
            "",
            "\141\30\1\16\2\30\1\17\7\30\1\20\1\30\1\21\1\22\1\23\1\30\1"+
            "\24\1\25\1\26\1\30\1\27\uff89\30",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\14",
            "\1\32\1\uffff\12\14\13\uffff\1\32\37\uffff\1\32",
            "",
            "\165\30\1\33\uff8a\30",
            "\145\30\1\34\uff9a\30",
            "\151\30\1\35\uff96\30",
            "\141\30\1\36\uff9e\30",
            "\160\30\1\37\uff8f\30",
            "\141\30\1\40\20\30\1\41\uff8d\30",
            "\145\30\1\42\uff9a\30",
            "\157\30\1\43\5\30\1\44\uff8a\30",
            "\141\30\1\45\uff9e\30",
            "\145\30\1\46\uff9a\30",
            "",
            "",
            "",
            "\164\30\1\47\uff8b\30",
            "\160\30\1\50\2\30\1\51\uff8c\30",
            "\143\30\1\52\uff9c\30",
            "\155\30\1\53\uff92\30",
            "\145\30\1\54\uff9a\30",
            "\162\30\1\55\uff8d\30",
            "\157\30\1\56\uff90\30",
            "\161\30\1\57\uff8e\30",
            "\165\30\1\60\uff8a\30",
            "\155\30\1\61\uff92\30",
            "\147\30\1\62\uff98\30",
            "\162\30\1\63\uff8d\30",
            "\150\30\1\64\uff97\30",
            "\145\30\1\65\uff9a\30",
            "\143\30\1\66\uff9c\30",
            "\145\30\1\67\uff9a\30",
            "\145\30\1\70\uff9a\30",
            "\162\30\1\71\uff8d\30",
            "\141\30\1\72\uff9e\30",
            "\152\30\1\73\uff95\30",
            "\165\30\1\74\uff8a\30",
            "\162\30\1\75\uff8d\30",
            "\155\30\1\76\uff92\30",
            "\163\30\1\77\uff8c\30",
            "\163\30\1\100\uff8c\30",
            "\157\30\1\101\uff90\30",
            "\156\30\1\102\uff91\30",
            "\162\30\1\103\uff8d\30",
            "\156\30\1\104\uff91\30",
            "\42\30\1\105\uffdd\30",
            "\141\30\1\106\uff9e\30",
            "\155\30\1\107\uff92\30",
            "\145\30\1\110\uff9a\30",
            "\151\30\1\111\uff96\30",
            "\143\30\1\112\uff9c\30",
            "\141\30\1\113\uff9e\30",
            "\42\30\1\114\uffdd\30",
            "\151\30\1\115\uff96\30",
            "\162\30\1\116\uff8d\30",
            "\144\30\1\117\uff9b\30",
            "\151\30\1\120\uff96\30",
            "\163\30\1\121\uff8c\30",
            "",
            "\164\30\1\123\uff8b\30",
            "\145\30\1\124\uff9a\30",
            "\143\30\1\125\uff9c\30",
            "\162\30\1\126\uff8d\30",
            "\145\30\1\127\uff9a\30",
            "\162\30\1\130\uff8d\30",
            "",
            "\157\30\1\132\uff90\30",
            "\42\30\1\133\uffdd\30",
            "\145\30\1\134\uff9a\30",
            "\160\30\1\135\uff8f\30",
            "\145\30\1\136\uff9a\30",
            "",
            "\151\30\1\137\uff96\30",
            "\164\30\1\140\uff8b\30",
            "\164\30\1\141\uff8b\30",
            "\145\30\1\142\uff9a\30",
            "\42\30\1\143\uffdd\30",
            "\171\30\1\144\uff86\30",
            "",
            "\156\30\1\145\uff91\30",
            "",
            "\156\30\1\147\uff91\30",
            "\164\30\1\150\uff8b\30",
            "\42\30\1\151\uffdd\30",
            "\156\30\1\152\uff91\30",
            "\145\30\1\153\uff9a\30",
            "\137\30\1\154\uffa0\30",
            "\155\30\1\155\uff92\30",
            "",
            "\42\30\1\157\uffdd\30",
            "\42\30\1\160\74\30\1\161\uffa0\30",
            "",
            "\143\30\1\162\uff9c\30",
            "\151\30\1\163\uff96\30",
            "",
            "\147\30\1\165\uff98\30",
            "\162\30\1\166\uff8d\30",
            "\160\30\1\167\uff8f\30",
            "\145\30\1\170\uff9a\30",
            "",
            "",
            "",
            "\162\30\1\173\uff8d\30",
            "\151\30\1\174\uff96\30",
            "\157\30\1\175\uff90\30",
            "",
            "\163\30\1\176\uff8c\30",
            "\163\30\1\177\uff8c\30",
            "\141\30\1\u0080\uff9e\30",
            "\156\30\1\u0081\uff91\30",
            "",
            "",
            "\145\30\1\u0082\uff9a\30",
            "\145\30\1\u0083\uff9a\30",
            "\156\30\1\u0084\uff91\30",
            "\171\30\1\u0085\uff86\30",
            "\42\30\1\u0086\uffdd\30",
            "\147\30\1\u0087\uff98\30",
            "\164\30\1\u0088\uff8b\30",
            "\161\30\1\u0089\uff8e\30",
            "\163\30\1\u008a\uff8c\30",
            "\42\30\1\u008b\uffdd\30",
            "\163\30\1\u008c\uff8c\30",
            "",
            "\145\30\1\u008e\uff9a\30",
            "\163\30\1\u008f\uff8c\30",
            "\165\30\1\u0090\uff8a\30",
            "\42\30\1\u0091\uffdd\30",
            "",
            "\164\30\1\u0093\uff8b\30",
            "",
            "\42\30\1\u0094\uffdd\30",
            "\42\30\1\u0095\uffdd\30",
            "\151\30\1\u0096\uff96\30",
            "",
            "",
            "\145\30\1\u0098\uff9a\30",
            "",
            "",
            "\162\30\1\u009b\uff8d\30",
            "",
            "\155\30\1\u009c\uff92\30",
            "",
            "",
            "\145\30\1\u009d\uff9a\30",
            "\42\30\1\u009e\74\30\1\u009f\22\30\1\u00a0\uff8d\30",
            "\155\30\1\u00a1\uff92\30",
            "",
            "\163\30\1\u00a3\uff8c\30",
            "\145\30\1\u00a4\uff9a\30",
            "\145\30\1\u00a5\uff9a\30",
            "",
            "\165\30\1\u00a6\uff8a\30",
            "\154\30\1\u00a7\uff93\30",
            "\156\30\1\u00a8\uff91\30",
            "\160\30\1\u00a9\uff8f\30",
            "\145\30\1\u00aa\uff9a\30",
            "\164\30\1\u00ab\uff8b\30",
            "\160\30\1\u00ac\uff8f\30",
            "\141\30\1\u00ad\uff9e\30",
            "\42\30\1\u00ae\uffdd\30",
            "\157\30\1\u00af\uff90\30",
            "\163\30\1\u00b0\uff8c\30",
            "",
            "\162\30\1\u00b2\uff8d\30",
            "\145\30\1\u00b3\uff9a\30",
            "",
            "\164\30\1\u00b4\uff8b\30",
            "\42\30\1\u00b5\uffdd\30",
            "\42\30\1\u00b6\uffdd\30",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | RULE_LONG | RULE_DOUBLE | RULE_STRING | RULE_WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_28 = input.LA(1);

                        s = -1;
                        if ( (LA14_28=='p') ) {s = 40;}

                        else if ( (LA14_28=='s') ) {s = 41;}

                        else if ( ((LA14_28>='\u0000' && LA14_28<='o')||(LA14_28>='q' && LA14_28<='r')||(LA14_28>='t' && LA14_28<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA14_160 = input.LA(1);

                        s = -1;
                        if ( (LA14_160=='e') ) {s = 164;}

                        else if ( ((LA14_160>='\u0000' && LA14_160<='d')||(LA14_160>='f' && LA14_160<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA14_164 = input.LA(1);

                        s = -1;
                        if ( (LA14_164=='l') ) {s = 167;}

                        else if ( ((LA14_164>='\u0000' && LA14_164<='k')||(LA14_164>='m' && LA14_164<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA14_19 = input.LA(1);

                        s = -1;
                        if ( (LA14_19=='a') ) {s = 32;}

                        else if ( (LA14_19=='r') ) {s = 33;}

                        else if ( ((LA14_19>='\u0000' && LA14_19<='`')||(LA14_19>='b' && LA14_19<='q')||(LA14_19>='s' && LA14_19<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA14_175 = input.LA(1);

                        s = -1;
                        if ( (LA14_175=='r') ) {s = 178;}

                        else if ( ((LA14_175>='\u0000' && LA14_175<='q')||(LA14_175>='s' && LA14_175<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA14_172 = input.LA(1);

                        s = -1;
                        if ( (LA14_172=='o') ) {s = 175;}

                        else if ( ((LA14_172>='\u0000' && LA14_172<='n')||(LA14_172>='p' && LA14_172<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA14_169 = input.LA(1);

                        s = -1;
                        if ( (LA14_169=='p') ) {s = 172;}

                        else if ( ((LA14_169>='\u0000' && LA14_169<='o')||(LA14_169>='q' && LA14_169<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA14_166 = input.LA(1);

                        s = -1;
                        if ( (LA14_166=='p') ) {s = 169;}

                        else if ( ((LA14_166>='\u0000' && LA14_166<='o')||(LA14_166>='q' && LA14_166<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA14_163 = input.LA(1);

                        s = -1;
                        if ( (LA14_163=='u') ) {s = 166;}

                        else if ( ((LA14_163>='\u0000' && LA14_163<='t')||(LA14_163>='v' && LA14_163<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA14_159 = input.LA(1);

                        s = -1;
                        if ( (LA14_159=='s') ) {s = 163;}

                        else if ( ((LA14_159>='\u0000' && LA14_159<='r')||(LA14_159>='t' && LA14_159<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA14_15 = input.LA(1);

                        s = -1;
                        if ( (LA14_15=='e') ) {s = 28;}

                        else if ( ((LA14_15>='\u0000' && LA14_15<='d')||(LA14_15>='f' && LA14_15<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA14_180 = input.LA(1);

                        s = -1;
                        if ( (LA14_180=='\"') ) {s = 182;}

                        else if ( ((LA14_180>='\u0000' && LA14_180<='!')||(LA14_180>='#' && LA14_180<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA14_178 = input.LA(1);

                        s = -1;
                        if ( (LA14_178=='t') ) {s = 180;}

                        else if ( ((LA14_178>='\u0000' && LA14_178<='s')||(LA14_178>='u' && LA14_178<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA14_103 = input.LA(1);

                        s = -1;
                        if ( (LA14_103=='c') ) {s = 114;}

                        else if ( ((LA14_103>='\u0000' && LA14_103<='b')||(LA14_103>='d' && LA14_103<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA14_114 = input.LA(1);

                        s = -1;
                        if ( (LA14_114=='i') ) {s = 124;}

                        else if ( ((LA14_114>='\u0000' && LA14_114<='h')||(LA14_114>='j' && LA14_114<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA14_124 = input.LA(1);

                        s = -1;
                        if ( (LA14_124=='e') ) {s = 131;}

                        else if ( ((LA14_124>='\u0000' && LA14_124<='d')||(LA14_124>='f' && LA14_124<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA14_131 = input.LA(1);

                        s = -1;
                        if ( (LA14_131=='s') ) {s = 138;}

                        else if ( ((LA14_131>='\u0000' && LA14_131<='r')||(LA14_131>='t' && LA14_131<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA14_138 = input.LA(1);

                        s = -1;
                        if ( (LA14_138=='\"') ) {s = 145;}

                        else if ( ((LA14_138>='\u0000' && LA14_138<='!')||(LA14_138>='#' && LA14_138<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA14_41 = input.LA(1);

                        s = -1;
                        if ( (LA14_41=='c') ) {s = 54;}

                        else if ( ((LA14_41>='\u0000' && LA14_41<='b')||(LA14_41>='d' && LA14_41<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA14_54 = input.LA(1);

                        s = -1;
                        if ( (LA14_54=='r') ) {s = 67;}

                        else if ( ((LA14_54>='\u0000' && LA14_54<='q')||(LA14_54>='s' && LA14_54<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA14_67 = input.LA(1);

                        s = -1;
                        if ( (LA14_67=='i') ) {s = 80;}

                        else if ( ((LA14_67>='\u0000' && LA14_67<='h')||(LA14_67>='j' && LA14_67<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA14_80 = input.LA(1);

                        s = -1;
                        if ( (LA14_80=='p') ) {s = 93;}

                        else if ( ((LA14_80>='\u0000' && LA14_80<='o')||(LA14_80>='q' && LA14_80<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA14_52 = input.LA(1);

                        s = -1;
                        if ( (LA14_52=='o') ) {s = 65;}

                        else if ( ((LA14_52>='\u0000' && LA14_52<='n')||(LA14_52>='p' && LA14_52<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA14_39 = input.LA(1);

                        s = -1;
                        if ( (LA14_39=='h') ) {s = 52;}

                        else if ( ((LA14_39>='\u0000' && LA14_39<='g')||(LA14_39>='i' && LA14_39<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA14_78 = input.LA(1);

                        s = -1;
                        if ( (LA14_78=='\"') ) {s = 91;}

                        else if ( ((LA14_78>='\u0000' && LA14_78<='!')||(LA14_78>='#' && LA14_78<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA14_65 = input.LA(1);

                        s = -1;
                        if ( (LA14_65=='r') ) {s = 78;}

                        else if ( ((LA14_65>='\u0000' && LA14_65<='q')||(LA14_65>='s' && LA14_65<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA14_40 = input.LA(1);

                        s = -1;
                        if ( (LA14_40=='e') ) {s = 53;}

                        else if ( ((LA14_40>='\u0000' && LA14_40<='d')||(LA14_40>='f' && LA14_40<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA14_66 = input.LA(1);

                        s = -1;
                        if ( (LA14_66=='d') ) {s = 79;}

                        else if ( ((LA14_66>='\u0000' && LA14_66<='c')||(LA14_66>='e' && LA14_66<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA14_53 = input.LA(1);

                        s = -1;
                        if ( (LA14_53=='n') ) {s = 66;}

                        else if ( ((LA14_53>='\u0000' && LA14_53<='m')||(LA14_53>='o' && LA14_53<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA14_92 = input.LA(1);

                        s = -1;
                        if ( (LA14_92=='n') ) {s = 103;}

                        else if ( ((LA14_92>='\u0000' && LA14_92<='m')||(LA14_92>='o' && LA14_92<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA14_79 = input.LA(1);

                        s = -1;
                        if ( (LA14_79=='e') ) {s = 92;}

                        else if ( ((LA14_79>='\u0000' && LA14_79<='d')||(LA14_79>='f' && LA14_79<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA14_64 = input.LA(1);

                        s = -1;
                        if ( (LA14_64=='i') ) {s = 77;}

                        else if ( ((LA14_64>='\u0000' && LA14_64<='h')||(LA14_64>='j' && LA14_64<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA14_77 = input.LA(1);

                        s = -1;
                        if ( (LA14_77=='o') ) {s = 90;}

                        else if ( ((LA14_77>='\u0000' && LA14_77<='n')||(LA14_77>='p' && LA14_77<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA14_94 = input.LA(1);

                        s = -1;
                        if ( (LA14_94=='\"') ) {s = 105;}

                        else if ( ((LA14_94>='\u0000' && LA14_94<='!')||(LA14_94>='#' && LA14_94<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA14_38 = input.LA(1);

                        s = -1;
                        if ( (LA14_38=='r') ) {s = 51;}

                        else if ( ((LA14_38>='\u0000' && LA14_38<='q')||(LA14_38>='s' && LA14_38<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA14_17 = input.LA(1);

                        s = -1;
                        if ( (LA14_17=='a') ) {s = 30;}

                        else if ( ((LA14_17>='\u0000' && LA14_17<='`')||(LA14_17>='b' && LA14_17<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA14_30 = input.LA(1);

                        s = -1;
                        if ( (LA14_30=='m') ) {s = 43;}

                        else if ( ((LA14_30>='\u0000' && LA14_30<='l')||(LA14_30>='n' && LA14_30<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA14_51 = input.LA(1);

                        s = -1;
                        if ( (LA14_51=='s') ) {s = 64;}

                        else if ( ((LA14_51>='\u0000' && LA14_51<='r')||(LA14_51>='t' && LA14_51<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA14_43 = input.LA(1);

                        s = -1;
                        if ( (LA14_43=='e') ) {s = 56;}

                        else if ( ((LA14_43>='\u0000' && LA14_43<='d')||(LA14_43>='f' && LA14_43<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA14_56 = input.LA(1);

                        s = -1;
                        if ( (LA14_56=='\"') ) {s = 69;}

                        else if ( ((LA14_56>='\u0000' && LA14_56<='!')||(LA14_56>='#' && LA14_56<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA14_90 = input.LA(1);

                        s = -1;
                        if ( (LA14_90=='n') ) {s = 101;}

                        else if ( ((LA14_90>='\u0000' && LA14_90<='m')||(LA14_90>='o' && LA14_90<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA14_101 = input.LA(1);

                        s = -1;
                        if ( (LA14_101=='\"') ) {s = 112;}

                        else if ( (LA14_101=='_') ) {s = 113;}

                        else if ( ((LA14_101>='\u0000' && LA14_101<='!')||(LA14_101>='#' && LA14_101<='^')||(LA14_101>='`' && LA14_101<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA14_125 = input.LA(1);

                        s = -1;
                        if ( (LA14_125=='n') ) {s = 132;}

                        else if ( ((LA14_125>='\u0000' && LA14_125<='m')||(LA14_125>='o' && LA14_125<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA14_115 = input.LA(1);

                        s = -1;
                        if ( (LA14_115=='o') ) {s = 125;}

                        else if ( ((LA14_115>='\u0000' && LA14_115<='n')||(LA14_115>='p' && LA14_115<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA14_104 = input.LA(1);

                        s = -1;
                        if ( (LA14_104=='i') ) {s = 115;}

                        else if ( ((LA14_104>='\u0000' && LA14_104<='h')||(LA14_104>='j' && LA14_104<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA14_93 = input.LA(1);

                        s = -1;
                        if ( (LA14_93=='t') ) {s = 104;}

                        else if ( ((LA14_93>='\u0000' && LA14_93<='s')||(LA14_93>='u' && LA14_93<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA14_132 = input.LA(1);

                        s = -1;
                        if ( (LA14_132=='\"') ) {s = 139;}

                        else if ( ((LA14_132>='\u0000' && LA14_132<='!')||(LA14_132>='#' && LA14_132<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA14_29 = input.LA(1);

                        s = -1;
                        if ( (LA14_29=='c') ) {s = 42;}

                        else if ( ((LA14_29>='\u0000' && LA14_29<='b')||(LA14_29>='d' && LA14_29<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA14_16 = input.LA(1);

                        s = -1;
                        if ( (LA14_16=='i') ) {s = 29;}

                        else if ( ((LA14_16>='\u0000' && LA14_16<='h')||(LA14_16>='j' && LA14_16<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA14_23 = input.LA(1);

                        s = -1;
                        if ( (LA14_23=='e') ) {s = 38;}

                        else if ( ((LA14_23>='\u0000' && LA14_23<='d')||(LA14_23>='f' && LA14_23<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA14_81 = input.LA(1);

                        s = -1;
                        if ( (LA14_81=='e') ) {s = 94;}

                        else if ( ((LA14_81>='\u0000' && LA14_81<='d')||(LA14_81>='f' && LA14_81<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA14_68 = input.LA(1);

                        s = -1;
                        if ( (LA14_68=='s') ) {s = 81;}

                        else if ( ((LA14_68>='\u0000' && LA14_68<='r')||(LA14_68>='t' && LA14_68<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA14_55 = input.LA(1);

                        s = -1;
                        if ( (LA14_55=='n') ) {s = 68;}

                        else if ( ((LA14_55>='\u0000' && LA14_55<='m')||(LA14_55>='o' && LA14_55<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 53 : 
                        int LA14_42 = input.LA(1);

                        s = -1;
                        if ( (LA14_42=='e') ) {s = 55;}

                        else if ( ((LA14_42>='\u0000' && LA14_42<='d')||(LA14_42>='f' && LA14_42<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 54 : 
                        int LA14_100 = input.LA(1);

                        s = -1;
                        if ( (LA14_100=='\"') ) {s = 111;}

                        else if ( ((LA14_100>='\u0000' && LA14_100<='!')||(LA14_100>='#' && LA14_100<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 55 : 
                        int LA14_75 = input.LA(1);

                        s = -1;
                        if ( (LA14_75=='r') ) {s = 88;}

                        else if ( ((LA14_75>='\u0000' && LA14_75<='q')||(LA14_75>='s' && LA14_75<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 56 : 
                        int LA14_88 = input.LA(1);

                        s = -1;
                        if ( (LA14_88=='y') ) {s = 100;}

                        else if ( ((LA14_88>='\u0000' && LA14_88<='x')||(LA14_88>='z' && LA14_88<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 57 : 
                        int LA14_49 = input.LA(1);

                        s = -1;
                        if ( (LA14_49=='m') ) {s = 62;}

                        else if ( ((LA14_49>='\u0000' && LA14_49<='l')||(LA14_49>='n' && LA14_49<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 58 : 
                        int LA14_62 = input.LA(1);

                        s = -1;
                        if ( (LA14_62=='a') ) {s = 75;}

                        else if ( ((LA14_62>='\u0000' && LA14_62<='`')||(LA14_62>='b' && LA14_62<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 59 : 
                        int LA14_36 = input.LA(1);

                        s = -1;
                        if ( (LA14_36=='m') ) {s = 49;}

                        else if ( ((LA14_36>='\u0000' && LA14_36<='l')||(LA14_36>='n' && LA14_36<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 60 : 
                        int LA14_87 = input.LA(1);

                        s = -1;
                        if ( (LA14_87=='\"') ) {s = 99;}

                        else if ( ((LA14_87>='\u0000' && LA14_87<='!')||(LA14_87>='#' && LA14_87<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 61 : 
                        int LA14_2 = input.LA(1);

                        s = -1;
                        if ( (LA14_2=='a') ) {s = 14;}

                        else if ( (LA14_2=='d') ) {s = 15;}

                        else if ( (LA14_2=='l') ) {s = 16;}

                        else if ( (LA14_2=='n') ) {s = 17;}

                        else if ( (LA14_2=='o') ) {s = 18;}

                        else if ( (LA14_2=='p') ) {s = 19;}

                        else if ( (LA14_2=='r') ) {s = 20;}

                        else if ( (LA14_2=='s') ) {s = 21;}

                        else if ( (LA14_2=='t') ) {s = 22;}

                        else if ( (LA14_2=='v') ) {s = 23;}

                        else if ( ((LA14_2>='\u0000' && LA14_2<='`')||(LA14_2>='b' && LA14_2<='c')||(LA14_2>='e' && LA14_2<='k')||LA14_2=='m'||LA14_2=='q'||LA14_2=='u'||(LA14_2>='w' && LA14_2<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 62 : 
                        int LA14_63 = input.LA(1);

                        s = -1;
                        if ( (LA14_63=='\"') ) {s = 76;}

                        else if ( ((LA14_63>='\u0000' && LA14_63<='!')||(LA14_63>='#' && LA14_63<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 63 : 
                        int LA14_50 = input.LA(1);

                        s = -1;
                        if ( (LA14_50=='s') ) {s = 63;}

                        else if ( ((LA14_50>='\u0000' && LA14_50<='r')||(LA14_50>='t' && LA14_50<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 64 : 
                        int LA14_37 = input.LA(1);

                        s = -1;
                        if ( (LA14_37=='g') ) {s = 50;}

                        else if ( ((LA14_37>='\u0000' && LA14_37<='f')||(LA14_37>='h' && LA14_37<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 65 : 
                        int LA14_22 = input.LA(1);

                        s = -1;
                        if ( (LA14_22=='a') ) {s = 37;}

                        else if ( ((LA14_22>='\u0000' && LA14_22<='`')||(LA14_22>='b' && LA14_22<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 66 : 
                        int LA14_27 = input.LA(1);

                        s = -1;
                        if ( (LA14_27=='t') ) {s = 39;}

                        else if ( ((LA14_27>='\u0000' && LA14_27<='s')||(LA14_27>='u' && LA14_27<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 67 : 
                        int LA14_14 = input.LA(1);

                        s = -1;
                        if ( (LA14_14=='u') ) {s = 27;}

                        else if ( ((LA14_14>='\u0000' && LA14_14<='t')||(LA14_14>='v' && LA14_14<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 68 : 
                        int LA14_113 = input.LA(1);

                        s = -1;
                        if ( (LA14_113=='r') ) {s = 123;}

                        else if ( ((LA14_113>='\u0000' && LA14_113<='q')||(LA14_113>='s' && LA14_113<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 69 : 
                        int LA14_123 = input.LA(1);

                        s = -1;
                        if ( (LA14_123=='e') ) {s = 130;}

                        else if ( ((LA14_123>='\u0000' && LA14_123<='d')||(LA14_123>='f' && LA14_123<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 70 : 
                        int LA14_171 = input.LA(1);

                        s = -1;
                        if ( (LA14_171=='\"') ) {s = 174;}

                        else if ( ((LA14_171>='\u0000' && LA14_171<='!')||(LA14_171>='#' && LA14_171<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 71 : 
                        int LA14_168 = input.LA(1);

                        s = -1;
                        if ( (LA14_168=='t') ) {s = 171;}

                        else if ( ((LA14_168>='\u0000' && LA14_168<='s')||(LA14_168>='u' && LA14_168<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 72 : 
                        int LA14_21 = input.LA(1);

                        s = -1;
                        if ( (LA14_21=='o') ) {s = 35;}

                        else if ( (LA14_21=='u') ) {s = 36;}

                        else if ( ((LA14_21>='\u0000' && LA14_21<='n')||(LA14_21>='p' && LA14_21<='t')||(LA14_21>='v' && LA14_21<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 73 : 
                        int LA14_157 = input.LA(1);

                        s = -1;
                        if ( (LA14_157=='m') ) {s = 161;}

                        else if ( ((LA14_157>='\u0000' && LA14_157<='l')||(LA14_157>='n' && LA14_157<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 74 : 
                        int LA14_152 = input.LA(1);

                        s = -1;
                        if ( (LA14_152=='m') ) {s = 156;}

                        else if ( ((LA14_152>='\u0000' && LA14_152<='l')||(LA14_152>='n' && LA14_152<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 75 : 
                        int LA14_155 = input.LA(1);

                        s = -1;
                        if ( (LA14_155=='e') ) {s = 157;}

                        else if ( ((LA14_155>='\u0000' && LA14_155<='d')||(LA14_155>='f' && LA14_155<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 76 : 
                        int LA14_165 = input.LA(1);

                        s = -1;
                        if ( (LA14_165=='n') ) {s = 168;}

                        else if ( ((LA14_165>='\u0000' && LA14_165<='m')||(LA14_165>='o' && LA14_165<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 77 : 
                        int LA14_161 = input.LA(1);

                        s = -1;
                        if ( (LA14_161=='e') ) {s = 165;}

                        else if ( ((LA14_161>='\u0000' && LA14_161<='d')||(LA14_161>='f' && LA14_161<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 78 : 
                        int LA14_156 = input.LA(1);

                        s = -1;
                        if ( (LA14_156=='\"') ) {s = 158;}

                        else if ( (LA14_156=='_') ) {s = 159;}

                        else if ( (LA14_156=='r') ) {s = 160;}

                        else if ( ((LA14_156>='\u0000' && LA14_156<='!')||(LA14_156>='#' && LA14_156<='^')||(LA14_156>='`' && LA14_156<='q')||(LA14_156>='s' && LA14_156<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 79 : 
                        int LA14_137 = input.LA(1);

                        s = -1;
                        if ( (LA14_137=='u') ) {s = 144;}

                        else if ( ((LA14_137>='\u0000' && LA14_137<='t')||(LA14_137>='v' && LA14_137<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 80 : 
                        int LA14_130 = input.LA(1);

                        s = -1;
                        if ( (LA14_130=='q') ) {s = 137;}

                        else if ( ((LA14_130>='\u0000' && LA14_130<='p')||(LA14_130>='r' && LA14_130<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 81 : 
                        int LA14_150 = input.LA(1);

                        s = -1;
                        if ( (LA14_150=='r') ) {s = 155;}

                        else if ( ((LA14_150>='\u0000' && LA14_150<='q')||(LA14_150>='s' && LA14_150<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 82 : 
                        int LA14_144 = input.LA(1);

                        s = -1;
                        if ( (LA14_144=='i') ) {s = 150;}

                        else if ( ((LA14_144>='\u0000' && LA14_144<='h')||(LA14_144>='j' && LA14_144<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 83 : 
                        int LA14_32 = input.LA(1);

                        s = -1;
                        if ( (LA14_32=='r') ) {s = 45;}

                        else if ( ((LA14_32>='\u0000' && LA14_32<='q')||(LA14_32>='s' && LA14_32<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 84 : 
                        int LA14_147 = input.LA(1);

                        s = -1;
                        if ( (LA14_147=='e') ) {s = 152;}

                        else if ( ((LA14_147>='\u0000' && LA14_147<='d')||(LA14_147>='f' && LA14_147<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 85 : 
                        int LA14_71 = input.LA(1);

                        s = -1;
                        if ( (LA14_71=='e') ) {s = 84;}

                        else if ( ((LA14_71>='\u0000' && LA14_71<='d')||(LA14_71>='f' && LA14_71<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 86 : 
                        int LA14_140 = input.LA(1);

                        s = -1;
                        if ( (LA14_140=='t') ) {s = 147;}

                        else if ( ((LA14_140>='\u0000' && LA14_140<='s')||(LA14_140>='u' && LA14_140<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 87 : 
                        int LA14_84 = input.LA(1);

                        s = -1;
                        if ( (LA14_84=='t') ) {s = 96;}

                        else if ( ((LA14_84>='\u0000' && LA14_84<='s')||(LA14_84>='u' && LA14_84<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 88 : 
                        int LA14_45 = input.LA(1);

                        s = -1;
                        if ( (LA14_45=='a') ) {s = 58;}

                        else if ( ((LA14_45>='\u0000' && LA14_45<='`')||(LA14_45>='b' && LA14_45<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 89 : 
                        int LA14_58 = input.LA(1);

                        s = -1;
                        if ( (LA14_58=='m') ) {s = 71;}

                        else if ( ((LA14_58>='\u0000' && LA14_58<='l')||(LA14_58>='n' && LA14_58<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 90 : 
                        int LA14_173 = input.LA(1);

                        s = -1;
                        if ( (LA14_173=='s') ) {s = 176;}

                        else if ( ((LA14_173>='\u0000' && LA14_173<='r')||(LA14_173>='t' && LA14_173<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 91 : 
                        int LA14_133 = input.LA(1);

                        s = -1;
                        if ( (LA14_133=='s') ) {s = 140;}

                        else if ( ((LA14_133>='\u0000' && LA14_133<='r')||(LA14_133>='t' && LA14_133<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 92 : 
                        int LA14_176 = input.LA(1);

                        s = -1;
                        if ( (LA14_176=='e') ) {s = 179;}

                        else if ( ((LA14_176>='\u0000' && LA14_176<='d')||(LA14_176>='f' && LA14_176<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 93 : 
                        int LA14_167 = input.LA(1);

                        s = -1;
                        if ( (LA14_167=='e') ) {s = 170;}

                        else if ( ((LA14_167>='\u0000' && LA14_167<='d')||(LA14_167>='f' && LA14_167<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 94 : 
                        int LA14_170 = input.LA(1);

                        s = -1;
                        if ( (LA14_170=='a') ) {s = 173;}

                        else if ( ((LA14_170>='\u0000' && LA14_170<='`')||(LA14_170>='b' && LA14_170<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 95 : 
                        int LA14_126 = input.LA(1);

                        s = -1;
                        if ( (LA14_126=='y') ) {s = 133;}

                        else if ( ((LA14_126>='\u0000' && LA14_126<='x')||(LA14_126>='z' && LA14_126<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 96 : 
                        int LA14_179 = input.LA(1);

                        s = -1;
                        if ( (LA14_179=='\"') ) {s = 181;}

                        else if ( ((LA14_179>='\u0000' && LA14_179<='!')||(LA14_179>='#' && LA14_179<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 97 : 
                        int LA14_117 = input.LA(1);

                        s = -1;
                        if ( (LA14_117=='s') ) {s = 126;}

                        else if ( ((LA14_117>='\u0000' && LA14_117<='r')||(LA14_117>='t' && LA14_117<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 98 : 
                        int LA14_106 = input.LA(1);

                        s = -1;
                        if ( (LA14_106=='g') ) {s = 117;}

                        else if ( ((LA14_106>='\u0000' && LA14_106<='f')||(LA14_106>='h' && LA14_106<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 99 : 
                        int LA14_46 = input.LA(1);

                        s = -1;
                        if ( (LA14_46=='j') ) {s = 59;}

                        else if ( ((LA14_46>='\u0000' && LA14_46<='i')||(LA14_46>='k' && LA14_46<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 100 : 
                        int LA14_33 = input.LA(1);

                        s = -1;
                        if ( (LA14_33=='o') ) {s = 46;}

                        else if ( ((LA14_33>='\u0000' && LA14_33<='n')||(LA14_33>='p' && LA14_33<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 101 : 
                        int LA14_97 = input.LA(1);

                        s = -1;
                        if ( (LA14_97=='_') ) {s = 108;}

                        else if ( ((LA14_97>='\u0000' && LA14_97<='^')||(LA14_97>='`' && LA14_97<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 102 : 
                        int LA14_85 = input.LA(1);

                        s = -1;
                        if ( (LA14_85=='t') ) {s = 97;}

                        else if ( ((LA14_85>='\u0000' && LA14_85<='s')||(LA14_85>='u' && LA14_85<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 103 : 
                        int LA14_95 = input.LA(1);

                        s = -1;
                        if ( (LA14_95=='n') ) {s = 106;}

                        else if ( ((LA14_95>='\u0000' && LA14_95<='m')||(LA14_95>='o' && LA14_95<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 104 : 
                        int LA14_72 = input.LA(1);

                        s = -1;
                        if ( (LA14_72=='c') ) {s = 85;}

                        else if ( ((LA14_72>='\u0000' && LA14_72<='b')||(LA14_72>='d' && LA14_72<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 105 : 
                        int LA14_83 = input.LA(1);

                        s = -1;
                        if ( (LA14_83=='i') ) {s = 95;}

                        else if ( ((LA14_83>='\u0000' && LA14_83<='h')||(LA14_83>='j' && LA14_83<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 106 : 
                        int LA14_59 = input.LA(1);

                        s = -1;
                        if ( (LA14_59=='e') ) {s = 72;}

                        else if ( ((LA14_59>='\u0000' && LA14_59<='d')||(LA14_59>='f' && LA14_59<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 107 : 
                        int LA14_127 = input.LA(1);

                        s = -1;
                        if ( (LA14_127=='\"') ) {s = 134;}

                        else if ( ((LA14_127>='\u0000' && LA14_127<='!')||(LA14_127>='#' && LA14_127<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 108 : 
                        int LA14_118 = input.LA(1);

                        s = -1;
                        if ( (LA14_118=='s') ) {s = 127;}

                        else if ( ((LA14_118>='\u0000' && LA14_118<='r')||(LA14_118>='t' && LA14_118<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 109 : 
                        int LA14_107 = input.LA(1);

                        s = -1;
                        if ( (LA14_107=='r') ) {s = 118;}

                        else if ( ((LA14_107>='\u0000' && LA14_107<='q')||(LA14_107>='s' && LA14_107<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 110 : 
                        int LA14_96 = input.LA(1);

                        s = -1;
                        if ( (LA14_96=='e') ) {s = 107;}

                        else if ( ((LA14_96>='\u0000' && LA14_96<='d')||(LA14_96>='f' && LA14_96<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 111 : 
                        int LA14_70 = input.LA(1);

                        s = -1;
                        if ( (LA14_70=='t') ) {s = 83;}

                        else if ( ((LA14_70>='\u0000' && LA14_70<='s')||(LA14_70>='u' && LA14_70<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 112 : 
                        int LA14_57 = input.LA(1);

                        s = -1;
                        if ( (LA14_57=='a') ) {s = 70;}

                        else if ( ((LA14_57>='\u0000' && LA14_57<='`')||(LA14_57>='b' && LA14_57<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 113 : 
                        int LA14_31 = input.LA(1);

                        s = -1;
                        if ( (LA14_31=='e') ) {s = 44;}

                        else if ( ((LA14_31>='\u0000' && LA14_31<='d')||(LA14_31>='f' && LA14_31<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 114 : 
                        int LA14_44 = input.LA(1);

                        s = -1;
                        if ( (LA14_44=='r') ) {s = 57;}

                        else if ( ((LA14_44>='\u0000' && LA14_44<='q')||(LA14_44>='s' && LA14_44<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 115 : 
                        int LA14_20 = input.LA(1);

                        s = -1;
                        if ( (LA14_20=='e') ) {s = 34;}

                        else if ( ((LA14_20>='\u0000' && LA14_20<='d')||(LA14_20>='f' && LA14_20<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 116 : 
                        int LA14_34 = input.LA(1);

                        s = -1;
                        if ( (LA14_34=='q') ) {s = 47;}

                        else if ( ((LA14_34>='\u0000' && LA14_34<='p')||(LA14_34>='r' && LA14_34<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 117 : 
                        int LA14_47 = input.LA(1);

                        s = -1;
                        if ( (LA14_47=='u') ) {s = 60;}

                        else if ( ((LA14_47>='\u0000' && LA14_47<='t')||(LA14_47>='v' && LA14_47<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 118 : 
                        int LA14_18 = input.LA(1);

                        s = -1;
                        if ( (LA14_18=='p') ) {s = 31;}

                        else if ( ((LA14_18>='\u0000' && LA14_18<='o')||(LA14_18>='q' && LA14_18<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 119 : 
                        int LA14_60 = input.LA(1);

                        s = -1;
                        if ( (LA14_60=='i') ) {s = 73;}

                        else if ( ((LA14_60>='\u0000' && LA14_60<='h')||(LA14_60>='j' && LA14_60<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 120 : 
                        int LA14_73 = input.LA(1);

                        s = -1;
                        if ( (LA14_73=='r') ) {s = 86;}

                        else if ( ((LA14_73>='\u0000' && LA14_73<='q')||(LA14_73>='s' && LA14_73<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 121 : 
                        int LA14_86 = input.LA(1);

                        s = -1;
                        if ( (LA14_86=='e') ) {s = 98;}

                        else if ( ((LA14_86>='\u0000' && LA14_86<='d')||(LA14_86>='f' && LA14_86<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 122 : 
                        int LA14_108 = input.LA(1);

                        s = -1;
                        if ( (LA14_108=='p') ) {s = 119;}

                        else if ( ((LA14_108>='\u0000' && LA14_108<='o')||(LA14_108>='q' && LA14_108<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 123 : 
                        int LA14_119 = input.LA(1);

                        s = -1;
                        if ( (LA14_119=='a') ) {s = 128;}

                        else if ( ((LA14_119>='\u0000' && LA14_119<='`')||(LA14_119>='b' && LA14_119<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 124 : 
                        int LA14_128 = input.LA(1);

                        s = -1;
                        if ( (LA14_128=='g') ) {s = 135;}

                        else if ( ((LA14_128>='\u0000' && LA14_128<='f')||(LA14_128>='h' && LA14_128<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 125 : 
                        int LA14_135 = input.LA(1);

                        s = -1;
                        if ( (LA14_135=='e') ) {s = 142;}

                        else if ( ((LA14_135>='\u0000' && LA14_135<='d')||(LA14_135>='f' && LA14_135<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 126 : 
                        int LA14_142 = input.LA(1);

                        s = -1;
                        if ( (LA14_142=='\"') ) {s = 148;}

                        else if ( ((LA14_142>='\u0000' && LA14_142<='!')||(LA14_142>='#' && LA14_142<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 127 : 
                        int LA14_48 = input.LA(1);

                        s = -1;
                        if ( (LA14_48=='r') ) {s = 61;}

                        else if ( ((LA14_48>='\u0000' && LA14_48<='q')||(LA14_48>='s' && LA14_48<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 128 : 
                        int LA14_35 = input.LA(1);

                        s = -1;
                        if ( (LA14_35=='u') ) {s = 48;}

                        else if ( ((LA14_35>='\u0000' && LA14_35<='t')||(LA14_35>='v' && LA14_35<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 129 : 
                        int LA14_74 = input.LA(1);

                        s = -1;
                        if ( (LA14_74=='e') ) {s = 87;}

                        else if ( ((LA14_74>='\u0000' && LA14_74<='d')||(LA14_74>='f' && LA14_74<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 130 : 
                        int LA14_61 = input.LA(1);

                        s = -1;
                        if ( (LA14_61=='c') ) {s = 74;}

                        else if ( ((LA14_61>='\u0000' && LA14_61<='b')||(LA14_61>='d' && LA14_61<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 131 : 
                        int LA14_109 = input.LA(1);

                        s = -1;
                        if ( (LA14_109=='e') ) {s = 120;}

                        else if ( ((LA14_109>='\u0000' && LA14_109<='d')||(LA14_109>='f' && LA14_109<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 132 : 
                        int LA14_98 = input.LA(1);

                        s = -1;
                        if ( (LA14_98=='m') ) {s = 109;}

                        else if ( ((LA14_98>='\u0000' && LA14_98<='l')||(LA14_98>='n' && LA14_98<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 133 : 
                        int LA14_129 = input.LA(1);

                        s = -1;
                        if ( (LA14_129=='t') ) {s = 136;}

                        else if ( ((LA14_129>='\u0000' && LA14_129<='s')||(LA14_129>='u' && LA14_129<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 134 : 
                        int LA14_120 = input.LA(1);

                        s = -1;
                        if ( (LA14_120=='n') ) {s = 129;}

                        else if ( ((LA14_120>='\u0000' && LA14_120<='m')||(LA14_120>='o' && LA14_120<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 135 : 
                        int LA14_143 = input.LA(1);

                        s = -1;
                        if ( (LA14_143=='\"') ) {s = 149;}

                        else if ( ((LA14_143>='\u0000' && LA14_143<='!')||(LA14_143>='#' && LA14_143<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 136 : 
                        int LA14_136 = input.LA(1);

                        s = -1;
                        if ( (LA14_136=='s') ) {s = 143;}

                        else if ( ((LA14_136>='\u0000' && LA14_136<='r')||(LA14_136>='t' && LA14_136<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}